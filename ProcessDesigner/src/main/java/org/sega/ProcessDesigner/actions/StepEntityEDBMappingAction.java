package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.sega.ProcessDesigner.models.ProcessTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="error",type="redirectAction", location="step1")
})
public class StepEntityEDBMappingAction extends EditStepAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5364807827254896140L;
	
	private String entityJSON = "";
	private String dbJSON = "";
	
	private String edb_connect_url = "";
	
	public String execute() throws Exception {

		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if(!session.containsKey("process_id"))
				return ERROR;
			entityJSON = (String) session.get("process_entityJSON");
			dbJSON = (String) session.get("process_dbJSON");
			if(session.containsKey("edb_connect_url"))
				edb_connect_url = (String) session.get("edb_connect_url");
			else
				edb_connect_url = "";
		} catch (Exception e) {
			return ERROR;
		}

		
		return SUCCESS;
	}
	
	public String submit() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}

	public String getEntityJSON() {
		return entityJSON;
	}

	public void setEntityJSON(String entityJSON) {

		this.entityJSON = entityJSON;
	}

	public String getEdb_connect_url() {
		return edb_connect_url;
	}

	public void setEdb_connect_url(String edb_connect_url) {
		this.edb_connect_url = edb_connect_url;
	}

	public String getDbJSON() {
		return dbJSON;
	}

	public void setDbJSON(String dbJSON) {
		this.dbJSON = dbJSON;
	}

}
