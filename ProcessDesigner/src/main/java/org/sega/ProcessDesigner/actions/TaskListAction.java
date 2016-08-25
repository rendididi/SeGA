package org.sega.ProcessDesigner.actions;

import java.util.*;

import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sega.ProcessDesigner.models.*;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.util.SaveLog;

public class TaskListAction extends ProcessDesignerSupport {
    public List<ProcessEdit> activities = new ArrayList<>();
    public ProcessEdit firstActivity = null;
    public long totalPages = 0L;
    public long total = 0L;

    private int page = 1;
    private int pageSize = 5;

    public long activityId;
    public String redirectAction;

    public String execute() throws Exception {
        getActivities(page);

        return SUCCESS;
    }

    public String selectProcess() throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ProcessEdit activity = (ProcessEdit) session
                .get("org.sega.ProcessDesigner.models.ProcessEdit", activityId);
//        session.createQuery("from ProcessEdit p where p.activityId = :activityId").setParameter(arg0, arg1);
        session.getTransaction().commit();

        getSession().put("process", activity.getProcess());
        getSession().put("edit", activity);

        // Dynamically redirect a certain step action based on the step name
        redirectAction = activity.getStep();
        
		SaveLog.saveLog(new Users((long)1),"11","活动选择",new Date(),"选择了一项活动,活动ID为:"+activityId+"对应的流程为:"+activity.getProcess().getId(),Constant.OTHER_OPERATION,this.getClass().getName());

        return SUCCESS;
    }

    private void getActivities(int page) throws Exception {
        Map<String, Object> contextSession = ActionContext.getContext().getSession();
        String userType = (String) contextSession.get("userType");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Criteria criteria = session
                .createCriteria(ProcessEdit.class)
                .add(Restrictions.not(Restrictions.eq("step", "published")))
                .add(Restrictions.not(Restrictions.eq("step", "template")))
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .addOrder(Order.desc("datetime"));

        if (!userType.isEmpty()) {
            //criteria.add(Restrictions.eq("userType", userType));
        }

        activities = (List<ProcessEdit>) criteria.list();

        firstActivity = (ProcessEdit) session
                .createCriteria(ProcessEdit.class)
                .addOrder(Order.desc("datetime"))
                .setMaxResults(1)
                .uniqueResult();

        total = (Long) session.createCriteria(ProcessEdit.class).setProjection(Projections.rowCount()).uniqueResult();
        totalPages = total / pageSize;
        if (total % pageSize != 0)
            ++totalPages;

        session.getTransaction().commit();
		SaveLog.saveLog(new Users((long)1),"11",Constant.SHOW_ALL,new Date(),"显示所有的活动列表，共有"+total+"条记录",Constant.SEARCH_OPERATION,this.getClass().getName());

    }
    
    //菜单刷选
    /*public filterProcess(){
    	
    }*/

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPageSize(int size) {
        this.pageSize = size;
    }

    public int getPageSize() {
        return pageSize;
    }

}
