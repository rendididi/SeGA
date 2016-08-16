package org.sega.ProcessDesigner.actions;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.sega.ProcessDesigner.models.ProcessEdit;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Base64Util;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.util.HumanTaskInterfaceGenerator;
import org.sega.ProcessDesigner.util.ProcessSchemaConvertUtil;
import org.sega.ProcessDesigner.util.SaveLog;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StepPublish extends EditStepAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3044031489648520111L;

	@Override
	public String submit() {
		try {
			org.sega.ProcessDesigner.models.Process sp = (org.sega.ProcessDesigner.models.Process) getSession().get("process");

			generateHumantask(sp);
			
			String entityJSON = Base64Util.decode(sp.getEntityJSON());
			String processJSON = Base64Util.decode(sp.getProcessJSON());
			String bindJSON = Base64Util.decode(sp.getBindingJSON());
			sp.setProcessXML(Base64Util.encode(ProcessSchemaConvertUtil.JSONtoXML(entityJSON, processJSON, bindJSON)));
			
			//update process edit item
			ProcessEdit edit;
			if(!getSession().containsKey("edit")){
				edit = new ProcessEdit();
			}else {
				edit = (ProcessEdit)getSession().get("edit");
			}
			edit.setDatetime(new Date());
			edit.setProcess(sp);

			edit.setStep("published");
			edit.setUserType(getUserType());
			
			//persist edit
			Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			hb_session.saveOrUpdate(edit);
			hb_session.saveOrUpdate(sp);
			hb_session.getTransaction().commit();
			
			getSession().put("edit", edit);
			SaveLog.saveLog(new Users((long)1),"11","将活动发布为一个流程",new Date(),"发布——将活动发布为一个流程,流程ID为:"+sp.getId(),Constant.PULISH_OPERATION,this.getClass().getName());

			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	private void generateHumantask(org.sega.ProcessDesigner.models.Process sp) throws Exception {
		String entity_json = Base64Util.decode(sp.getEntityJSON()), 
			bind_json = Base64Util.decode(sp.getBindingJSON());
		
		JSONObject entity = JSONArray.fromObject(entity_json).getJSONObject(0);
		JSONArray binding = JSONArray.fromObject(bind_json);
		
		for (int i = 0; i < binding.size(); i++) {
			JSONObject bo = binding.getJSONObject(i);
			List<String> read,write;
			if(bo.has("read"))
				read = (List<String>) JSONArray.toCollection(bo.optJSONArray("read"));
			else
				read = new ArrayList<String>();
			
			if(bo.has("write"))
				write = (List<String>) JSONArray.toCollection(bo.optJSONArray("write"));
			else
				write = new ArrayList<String>();

			if (bo.has("autoGenerate")) { //need a better way to determine if task is not web service

				boolean autoGenerate = bo.getBoolean("autoGenerate");
				String taskid = bo.getString("task");

				if (autoGenerate) {
					HumanTaskInterfaceGenerator gen = new HumanTaskInterfaceGenerator();
					String html = gen.generate(entity, read, write);

					URL location = StepPublish.class.getProtectionDomain().getCodeSource().getLocation();
					File dir = new File(FilenameUtils.concat(location.getPath(),
							"../../../TaskViewer/src/main/resources/templates/fragments/humantask/"
									+ sp.getId().toString()));
					dir.mkdirs();
					File file = new File(dir, taskid + ".html");
					FileUtils.writeStringToFile(file, html, "UTF-8");
				}
			}
		}

	}
	
	public String publishTemplate() {
		try {
			org.sega.ProcessDesigner.models.Process sp = (org.sega.ProcessDesigner.models.Process) getSession().get("process");
			ProcessTemplate template = new ProcessTemplate();
			template.setProcessJSON(sp.getProcessJSON());
			template.setDatabaseSQL(sp.getDatabaseJSON());
			template.setEDmappingJSON(sp.getEDmappingJSON());
			template.setEntityJSON(sp.getEntityJSON());
			template.setName(sp.getName());
			template.setProcessImageUrl(sp.getProcessImageUrl());
			
			//update process edit item
			ProcessEdit edit;
			if(!getSession().containsKey("edit")){
				edit = new ProcessEdit();
			}else {
				edit = (ProcessEdit)getSession().get("edit");
			}
			edit.setDatetime(new Date());
			edit.setProcess(sp);

			edit.setStep("template");
			edit.setUserType(getUserType());
			
			//persist edit
			Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			hb_session.saveOrUpdate(edit);
			hb_session.saveOrUpdate(template);
			hb_session.getTransaction().commit();
			
			getSession().put("edit", edit);
			SaveLog.saveLog(new Users((long)1),"11","将活动发布为一个模板",new Date(),"将活动发布为一个模板,模板ID为:"+template.getId(),Constant.PULISH_OPERATION,this.getClass().getName());

			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
}
