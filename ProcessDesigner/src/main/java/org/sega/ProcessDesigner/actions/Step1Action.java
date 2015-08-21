package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class Step1Action extends ActionSupport {

	private static final long serialVersionUID = 3047873710625821949L;

	private List<ProcessTemplate> process_list;
	
	private long process_id;
	
	// view
	public String execute() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		process_list = (List<ProcessTemplate>)session.createCriteria(ProcessTemplate.class).list();
		session.getTransaction().commit();
        return SUCCESS;
    }
	
	// submit
	public String submit() {
		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			Session hb_session = HibernateUtil.getSessionFactory().getCurrentSession();
			hb_session.beginTransaction();
			ProcessTemplate process = (ProcessTemplate)hb_session.get(
					"org.sega.ProcessDesigner.models.ProcessTemplate", process_id);
			String entityJSONBase64 = process.getEntityJSON();
			String entityJSON = new String(Base64.getDecoder().decode(entityJSONBase64), "UTF-8");
			
			session.put("process_id",process.getId());
			session.put("process_entityJSON", entityJSON);
			
			
			hb_session.getTransaction().commit();
		} catch (Exception e) {
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
	

	
	

}
