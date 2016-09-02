package org.sega.ProcessDesigner.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javassist.compiler.ProceedHandler;

import org.apache.struts2.dispatcher.Dispatcher;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.ProcessEdit;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.ExceptionUtil;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.util.SaveLog;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;


public class StepProcessSelectAction extends ProcessDesignerSupport {

	private static final long serialVersionUID = 3047873710625821949L;

	private List<ProcessTemplate> process_list;
	
	private long process_id;
	private String processType;
	// view
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		if(processType != null){
			System.out.println("bu null");
			process_list = (List<ProcessTemplate>)session.createCriteria(ProcessTemplate.class).add(Restrictions.like("name", "%"+processType+"%")).list();

		}else{
			System.out.println("null");
			process_list = (List<ProcessTemplate>)session.createCriteria(ProcessTemplate.class).list();
		}
		session.getTransaction().commit();
		System.out.println(processType+"--processType"+process_list.size());
		String logContent;
		logContent = "模板选择——显示所有的流程模板信息";
		SaveLog.saveLog(new Users((long)1),"11",Constant.SHOW_ALL,new Date(),logContent,Constant.SEARCH_OPERATION,this.getClass().getName());
		
        return SUCCESS;
    }
	
	// submit
	public String submit() {
		
		try {
			Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			ProcessTemplate process_t = (ProcessTemplate)hb_session.get(
					"org.sega.ProcessDesigner.models.ProcessTemplate", process_id);
			hb_session.getTransaction().commit();
			Process process = new Process();
			
			process.setName(getProcess().getName());
			process.setDescription(getProcess().getDescription());
			process.setTemplate(process_t);
			process.setEntityJSON(process.getTemplate().getEntityJSON());
			process.setProcessJSON(process.getTemplate().getProcessJSON());
			process.setEDmappingJSON(process_t.getEDmappingJSON());
			getSession().put("process", process);
			//persist process
			hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			hb_session.saveOrUpdate(process);
			hb_session.getTransaction().commit();
			
			//save persisted process in session
			getSession().put("process", process);
			
			//update process edit item
			ProcessEdit edit;
			edit = new ProcessEdit();
			edit.setCity((String) ActionContext.getContext().getSession().get("city"));
			
			if(process_t.getName().contains(Constant.FY)){
				edit.setType("11");
			}
			if(process_t.getName().contains(Constant.SQ)){
				edit.setType("22");
			}
			if(process_t.getName().contains(Constant.PZ)){
				edit.setType("33");
			}
			if(process_t.getName().contains(Constant.JJ)){
				edit.setType("44");
			}
			edit.setDatetime(new Date());
			edit.setProcess(process);
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
			
			SaveLog.saveLog(new Users((long)1),"11",Constant.SUBMIT_CONTENT,new Date(),"模板选择——选择并提交了一个流程模板,模板ID为:"+process_t.getId()+",流程ID为："+process_id+",更新的流程内容为:名称"+getProcess().getName()+",描述:"+getProcess().getDescription()+",EntityJson"+getProcess().getEntityJSON()+",ProcessJson:"+getProcess().getEntityJSON()+"DDMapping:"+getProcess().getDDmappingJSON()+"模板等",Constant.SUBMIT_OERATION,this.getClass().getName());
			
			getSession().put("edit", edit);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<ProcessTemplate> getProcess_list() {
		return process_list;
	}

	public void setProcess_list(List<ProcessTemplate> process_list) {
		this.process_list = process_list;
	}

	public long getProcess_id() {
		return process_id;
	}

	public void setProcess_id(long process_id) {
		this.process_id = process_id;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
}
