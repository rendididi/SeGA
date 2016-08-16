package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.SaveLog;

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
		SaveLog.saveLog(new Users((long)1),"22","实体-企业数据库-映射",new Date(),"实体-企业数据库-映射——流程更新,更新的流程ID为:"+sp.getId()+",更新的流程内容为:EDBmappingJSON:"+getProcess().getEDmappingJSON()+"EntityJSON:"+getProcess().getEntityJSON(),Constant.DATA_HANDLE+Constant.UPDATE_OPERATION,this.getClass().getName());

	}
}
