package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.ProcessTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
	@Result(name="error",type="redirectAction", location="step1")
})
public class StepEntityEDBMappingAction extends EditStepAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5364807827254896140L;
	
	@Override
	public void updateProcess(Process sp) throws Exception {
		sp.setEDmappingJSON(getProcess().getEDmappingJSON());
		sp.setEntityJSON(getProcess().getEntityJSON());
	}
}
