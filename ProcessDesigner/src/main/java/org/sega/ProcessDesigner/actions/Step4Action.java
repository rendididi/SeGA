package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Map;

import org.sega.ProcessDesigner.models.ProcessTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Step4Action extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2715550204984464046L;
	
	private String processJSON;
	
	public String execute() throws Exception {

		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			ProcessTemplate process = (ProcessTemplate)session.get("process");
			processJSON = new String(Base64.getDecoder().decode(process.getProcessJSON()),"UTF-8");
		} catch (Exception e) {
			return ERROR;
		}

		
		return SUCCESS;
	}

	public String getProcessJSON() {
		return processJSON;
	}

	public void setProcessJSON(String processJSON) {
		this.processJSON = processJSON;
	}


}
