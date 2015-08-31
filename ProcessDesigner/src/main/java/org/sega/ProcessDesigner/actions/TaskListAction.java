package org.sega.ProcessDesigner.actions;

import java.util.*;

import com.opensymphony.xwork2.ActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.models.ProcessEdit;

public class TaskListAction extends ProcessDesignerSupport {
    public List<ProcessEdit> activities = new ArrayList<>();
    public long totalPages = 0L;
    public long total = 0L;

    private int page = 1;
    private int pageSize = 5;

    public String execute() throws Exception {
        getActivities(page);

        return SUCCESS;
    }

    private void getActivities(int page) throws Exception {
        Map<String, Object> contextSession = ActionContext.getContext().getSession();
        String userType = (String) contextSession.get("userType");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(ProcessEdit.class);
        criteria.setFirstResult((page - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        if (!userType.isEmpty()) {
            criteria.add(Restrictions.eq("userType", userType));
        }

        activities = (List<ProcessEdit>) criteria.list();

        total = (Long) session.createCriteria(ProcessEdit.class).setProjection(Projections.rowCount()).uniqueResult();
        totalPages = total / pageSize;

        session.getTransaction().commit();
    }

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
