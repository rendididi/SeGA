package org.sega.ProcessDesigner.actions;

import java.util.List;

import org.hibernate.Session;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.util.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

public class Step1Action extends ActionSupport {

	private static final long serialVersionUID = 3047873710625821949L;

	private List<ProcessTemplate> process_list;
	
	public String execute() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		process_list = (List<ProcessTemplate>)session.createCriteria(ProcessTemplate.class).list();
		session.getTransaction().commit();
        return SUCCESS;
    }

	public List<ProcessTemplate> getProcess_list() {
		return process_list;
	}

	public void setProcess_list(List<ProcessTemplate> process_list) {
		this.process_list = process_list;
	}

}
