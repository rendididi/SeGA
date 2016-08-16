package org.sega.ProcessDesigner.util;

import java.util.Date;

import org.hibernate.Session;
import org.sega.ProcessDesigner.models.Log;
import org.sega.ProcessDesigner.models.Users;

public class SaveLog {
	public static void saveLog(Users user, String type,String descriptions, Date date,String content, String operationType, String name) {
		Session www_session = HibernateUtil.getSessionFactory().getCurrentSession();
		Log log = new Log();
		log.setUser(new Users((long) 1));
		log.setType(type);
		try{
			log.setContent(content);
			log.setOperationType(operationType);
			
		}catch(Exception e){
			log.setContent(ExceptionUtil.getExceptionAllinformation(e));
			log.setOperationType(Constant.EXCEPTION_OPERATION);
			e.printStackTrace();
		}
		log.setDescriptions(descriptions);
		log.setDate(date);
		log.setClassName(name);
		www_session.beginTransaction();
		www_session.saveOrUpdate(log);
		www_session.getTransaction().commit();
	}

}
