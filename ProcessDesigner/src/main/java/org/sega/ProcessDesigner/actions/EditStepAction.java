package org.sega.ProcessDesigner.actions;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.struts2.dispatcher.Dispatcher;
import org.hibernate.Session;
import org.sega.ProcessDesigner.models.ProcessEdit;
import org.sega.ProcessDesigner.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;

public class EditStepAction extends ProcessDesignerSupport {

	public String execute() throws Exception {
		try {
			if(!getSession().containsKey("process")){
				return ERROR;
			}
			org.sega.ProcessDesigner.models.Process process = (org.sega.ProcessDesigner.models.Process) getSession().get("process");
			setProcess(SerializationUtils.clone(process));
			
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String submit() {
		try {
			//update session process
			org.sega.ProcessDesigner.models.Process sp = (org.sega.ProcessDesigner.models.Process) getSession().get("process");
			updateProcess(sp);

			//persist process
			Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			hb_session.saveOrUpdate(sp);
			hb_session.getTransaction().commit();
			
			//save persisted process in session
			getSession().put("process", sp);
			
			//update process edit item
			ProcessEdit edit;
			if(!getSession().containsKey("edit")){
				edit = new ProcessEdit();
			}else {
				edit = (ProcessEdit)getSession().get("edit");
			}
			edit.setDatetime(new Date());
			edit.setProcess(sp);
			//get next action in struts.xml
			Map<String, ActionConfig> config_map = Dispatcher.getInstance().getConfigurationManager().getConfiguration().getPackageConfig("process-designer-support").getActionConfigs();
			ActionConfig action_config = config_map.get(ActionContext.getContext().getName());
			ResultConfig result_config = action_config.getResults().get(SUCCESS);
			edit.setStep(result_config.getParams().get("actionName"));
			edit.setUserType(getUserType());
			
			//persist edit
			hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			hb_session.saveOrUpdate(edit);
			hb_session.getTransaction().commit();
			
			getSession().put("edit", edit);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}

	public void updateProcess(org.sega.ProcessDesigner.models.Process sp) throws Exception {
		
	}
	
}
