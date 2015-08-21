package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.sega.ProcessDesigner.models.ProcessTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="error",type="redirectAction", location="step1")
})
public class Step2Action extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7111980754311792137L;
	
	private String entityJSON;
	
	public String execute() throws Exception {

		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if(!session.containsKey("process_id"))
				return ERROR;
			entityJSON = (String) session.get("process_entityJSON");
		} catch (Exception e) {
			return ERROR;
		}

		
		return SUCCESS;
	}

	public String submit() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("process_entityJSON", entityJSON);
		} catch (Exception e) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String getEntityJSON() {
		return entityJSON;
	}

	public void setEntityJSON(String entityJSON) {

		this.entityJSON = entityJSON;
	}

}
