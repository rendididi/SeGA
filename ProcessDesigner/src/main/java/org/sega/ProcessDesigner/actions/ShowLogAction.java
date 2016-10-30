package org.sega.ProcessDesigner.actions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sega.ProcessDesigner.models.Log;
import org.sega.ProcessDesigner.util.HibernateUtil;

public class ShowLogAction extends ProcessDesignerSupport{
	public List<Log> logs = new ArrayList<Log>();
	public List<Log> log1 = new ArrayList<Log>();
	public List<Log> log2 = new ArrayList<Log>();
	public List<Log> log3 = new ArrayList<Log>();
	
	public long totalPages1 = 0L;
	public long total1 = 0L;
	public long totalPages2 = 0L;
	public long total2 = 0L;
	public long totalPages3 = 0L;
	public long total3 = 0L;
	private int page = 1;
	private int pageSize = 5;
	
	public String execute() throws Exception{
		getLogs(page);
		//getLogs();
		return SUCCESS;
	}

	private void getLogs(int page) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Criteria criteria1 = session
				.createCriteria(Log.class)
				.add(Restrictions.eq("type", "11"))
				.addOrder(Order.desc("date"))
		        .setFirstResult((page - 1) * pageSize)
		        .setMaxResults(pageSize);
		Criteria criteria2 = session
				.createCriteria(Log.class)
				.add(Restrictions.eq("type", "22"))
				.setFirstResult((page - 1) * pageSize)
		        .setMaxResults(pageSize)
				.addOrder(Order.desc("date"));
		Criteria criteria3 = session
				.createCriteria(Log.class)
				.add(Restrictions.eq("type", "33"))
				.setFirstResult((page - 1) * pageSize)
		        .setMaxResults(pageSize)
				.addOrder(Order.desc("date"));
		
		log1 = (List<Log>) criteria1.list();
		log2 = (List<Log>) criteria2.list();
		log3 = (List<Log>) criteria3.list();
		
		total1 = log1.size();
		totalPages1 = total1 / pageSize;
		if (total1 % pageSize != 0)
            ++totalPages1;
		
		
		total2 = log2.size();
		totalPages2 = total2 / pageSize;
		if (total2 % pageSize != 0)
            ++totalPages2;
		
		total3 = log3.size();
		totalPages3 = total3 / pageSize;
		if (total3 % pageSize != 0)
            ++totalPages3;
		session.getTransaction().commit();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Log> getLog1() {
		return log1;
	}

	public void setLog1(List<Log> log1) {
		this.log1 = log1;
	}

	public List<Log> getLog2() {
		return log2;
	}

	public void setLog2(List<Log> log2) {
		this.log2 = log2;
	}

	public List<Log> getLog3() {
		return log3;
	}

	public void setLog3(List<Log> log3) {
		this.log3 = log3;
	}
	
}
