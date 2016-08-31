/**
 * 
 */
package org.sega.ProcessDesigner.actions;

import java.util.Date;
import java.util.Map;

import org.hibernate.Session;
import org.sega.ProcessDesigner.models.Log;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.HibernateUtil;
import org.sega.ProcessDesigner.util.SaveLog;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author glp
 *
 */
public class LoginAction extends ActionSupport {
	
	private String userType;
	private String processCity;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String doLogin() {
		if(!("expert".equals(userType)||"developer".equals(userType))){
			return LOGIN;
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.clear();
		session.put("userType", userType);
		session.put("city", processCity);
		SaveLog.saveLog(new Users((long)1),"11","用户登录",new Date(),"用户登录,登录用户类型为:"+userType,"登录操作",this.getClass().getName());
		
		return SUCCESS;
	}

	public String doLogout() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.clear();
		
		return LOGIN;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProcessCity() {
		return processCity;
	}

	public void setProcessCity(String processCity) {
		this.processCity = processCity;
	}
}
