/**
 * 
 */
package org.sega.ProcessDesigner.actions;

import java.util.Map;

import javax.servlet.http.Cookie;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author glp
 *
 */
public class LoginAction extends ActionSupport {
	
	private String userType;
	
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
	
	
}
