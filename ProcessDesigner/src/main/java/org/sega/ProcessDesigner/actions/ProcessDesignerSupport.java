/**
 * 
 */
package org.sega.ProcessDesigner.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author glp
 *
 */
public class ProcessDesignerSupport extends ActionSupport implements SessionAware{

	private Map<String, Object> session;
	
	private String userType;
	
	private org.sega.ProcessDesigner.models.Process process;

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public org.sega.ProcessDesigner.models.Process getProcess() {
		return process;
	}

	public void setProcess(org.sega.ProcessDesigner.models.Process process) {
		this.process = process;
	}

}
