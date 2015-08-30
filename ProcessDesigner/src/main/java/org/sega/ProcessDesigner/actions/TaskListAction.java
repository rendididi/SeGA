package org.sega.ProcessDesigner.actions;

import java.util.*;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.sega.ProcessDesigner.util.Base64Util;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.models.ProcessEdit;

public class TaskListAction extends ProcessDesignerSupport {
  public List<ProcessEdit> activities = new ArrayList<>();
  public int total = 0;

	public String execute() throws Exception {
		initialize();

		return SUCCESS;
	}

	private void initialize() throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		activities = (List<ProcessEdit>) session.createCriteria(ProcessEdit.class).list();
		session.getTransaction().commit();

    total = activities.size();
	}
}
