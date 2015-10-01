package org.sega.ProcessDesigner.actions;

import java.util.Map;

import org.sega.ProcessDesigner.models.Process;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StepBindProcessAction extends EditStepAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6926767002223497942L;

	@Override
	public void updateProcess(Process sp) throws Exception {
		sp.setBindingJSON(getProcess().getBindingJSON());
	}
}
