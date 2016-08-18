package org.sega.ProcessDesigner.actions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.sega.ProcessDesigner.models.Log;
import org.sega.ProcessDesigner.util.HibernateUtil;

public class ShowLogAction extends ProcessDesignerSupport{
	public List<Log> logs = new ArrayList<Log>();
	public List<Log> log1 = new ArrayList<Log>();
	public List<Log> log2 = new ArrayList<Log>();
	public List<Log> log3 = new ArrayList<Log>();
	
	public long totalPages = 0L;
	public long total = 0L;
	private int page = 1;
	private int pageSize = 15;
	
	public String execute() throws Exception{
		//getLogs(page);
		getLogs();
		return SUCCESS;
	}

	private void getLogs() throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Criteria criteria = session
				.createCriteria(Log.class)
				.addOrder(Order.desc("date"));
		
		logs = (List<Log>) criteria.list();
		/*for(int i =0;i<logs.size();i++){
			if(logs.get(i).getType().equals("11"))
				log1.add(logs.get(i));
			if(logs.get(i).getType().equals("22"))
				log2.add(logs.get(i));
			if(logs.get(i).getType().equals("33"))
				log3.add(logs.get(i));
		}*/
	
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
