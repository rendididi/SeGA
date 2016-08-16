package org.sega.ProcessDesigner.actions;

import java.util.Date;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONObject;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.SaveLog;

@Results({
	@Result(name="error",type="redirectAction", location="step-process-select")
})
public class StepCustomEntityAction extends EditStepAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7111980754311792137L;
	
	@Override
	public void updateProcess(org.sega.ProcessDesigner.models.Process sp) {
		SaveLog.saveLog(new Users((long)1),"22",Constant.UPDATE_CONTENT,new Date(),"自定义实体——更新的流程ID为:"+sp.getId()+",更新的内容为:EntityJson,详细内容如下："+getProcess().getEntityJSON(),Constant.UPDATE_OPERATION,this.getClass().getName());
		sp.setEntityJSON(getProcess().getEntityJSON());
	}
	
}
