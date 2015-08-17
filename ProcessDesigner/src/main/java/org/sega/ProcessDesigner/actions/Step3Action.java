package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Map;

import org.sega.ProcessDesigner.models.ProcessTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Step3Action extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5364807827254896140L;
	
	private String entityJSON;
	
	public String execute() throws Exception {

		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			ProcessTemplate process = (ProcessTemplate)session.get("process");
			entityJSON = new String(Base64.getDecoder().decode(process.getEntityJSON()),"UTF-8");
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
