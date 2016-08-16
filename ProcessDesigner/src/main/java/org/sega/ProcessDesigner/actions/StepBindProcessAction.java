package org.sega.ProcessDesigner.actions;

import java.util.Date;

import org.json.JSONObject;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.SaveLog;

public class StepBindProcessAction extends EditStepAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6926767002223497942L;

	@Override
	public void updateProcess(Process sp) throws Exception {
		sp.setBindingJSON(getProcess().getBindingJSON());
		SaveLog.saveLog(new Users((long)1),"11","流程绑定",new Date(),"流程绑定——流程更新,更新的流程ID为:"+sp.getId()+",更新的内容为,BingJson:"+getProcess().getBindingJSON(),Constant.UPDATE_OPERATION,this.getClass().getName());

	}
}
