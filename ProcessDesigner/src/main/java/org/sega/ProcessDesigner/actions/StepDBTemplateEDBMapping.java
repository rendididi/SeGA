package org.sega.ProcessDesigner.actions;

import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.util.Base64Util;

public class StepDBTemplateEDBMapping extends EditStepAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6071295152833939526L;

	@Override
	public void updateProcess(Process sp) throws Exception {
		sp.setDatabaseJSON(getProcess().getDatabaseJSON());
		sp.setDDmappingJSON(getProcess().getDDmappingJSON());
		
		//Auto generate ED Mapping Rules
		String dd_mapping_json = Base64Util.decode(getProcess().getDDmappingJSON());
		String ed_mapping_json = Base64Util.decode(sp.getTemplate().getEDmappingJSON());
		
		JSONArray dd, ed, ed2 = new JSONArray();
		
		try {
			dd = new JSONArray(dd_mapping_json);
		} catch (JSONException e) {
			dd = new JSONArray();
		}
		
		try {
			ed = new JSONArray(ed_mapping_json);
		} catch (JSONException e) {
			ed = new JSONArray();
		}

		for(int i=0; i<ed.length(); i++){
			JSONObject rule = ed.getJSONObject(i);
			JSONObject db = rule.getJSONObject("db");
			String ed_table = db.getString("table");
			String ed_column = db.getString("column");
			for(int j=0; j<dd.length(); j++){
				JSONObject dd_rule = dd.getJSONObject(j);
				JSONObject db1 = dd_rule.getJSONObject("db1");
				JSONObject db2 = dd_rule.getJSONObject("db2");
				if(ed_table.equals(db1.getString("table")) && ed_column.equals(db1.getString("column"))){
					JSONObject new_rule = new JSONObject(rule, new String[]{"db","entity"});
					JSONObject new_db = new_rule.getJSONObject("db");
					new_db.put("table", db2.getString("table"));
					new_db.put("column", db2.getString("column"));
					ed2.put(new_rule);
					break;
				}
			}
		}
		
		sp.setEDmappingJSON(Base64Util.encode(ed2.toString()));
	}
	
	@Override
	public String execute() throws Exception {
		String r = super.execute();
		dbt_json = Base64Util.decode(getProcess().getTemplate().getDatabaseSQL());
		return r;
	}
	
	
	
	
	private String dbt_json;
	
	public String getDbt_json() {
		return dbt_json;
	}

	public void setDbt_json(String dbt_json) {
		this.dbt_json = dbt_json;
	}


	
}
