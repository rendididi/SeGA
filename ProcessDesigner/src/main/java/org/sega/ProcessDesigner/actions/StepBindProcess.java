package org.sega.ProcessDesigner.actions;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StepBindProcess extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6926767002223497942L;
	private String processJSON;
	@Override
	public String execute() throws Exception {
		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			processJSON = (String)session.get("process_processJSON");
			
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
