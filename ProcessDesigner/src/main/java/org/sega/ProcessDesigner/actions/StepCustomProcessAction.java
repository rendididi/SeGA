package org.sega.ProcessDesigner.actions;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.Users;
import org.sega.ProcessDesigner.util.Constant;
import org.sega.ProcessDesigner.util.SaveLog;

public class StepCustomProcessAction extends EditStepAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2715550204984464046L;
	
	@Override
	public void updateProcess(Process sp) throws Exception {
		String random_name = "p_svg_"+sp.getId()+"_"+RandomStringUtils.randomAlphanumeric(32)+".svg";
		ServletContext context = ServletActionContext.getServletContext();
		String path = context.getRealPath(context.getContextPath());
		File file = Paths.get(path, "images","process",random_name).toFile();
		FileUtils.writeStringToFile(file, svg,"utf-8");
		
		sp.setProcessImageUrl(random_name);
		sp.setProcessJSON(getProcess().getProcessJSON());
		SaveLog.saveLog(new Users((long)1),"22","自定义流程",new Date(),"自定义流程——流程更新,更新的流程ID为:"+sp.getId()+",更新的内容为:ProcessImageUrl:"+random_name+"ProcessJson:"+getProcess().getProcessJSON(),Constant.UPDATE_OPERATION,this.getClass().getName());

	}
	
	private String svg;

	public String getSvg() {
		return svg;
	}

	public void setSvg(String svg) {
		this.svg = svg;
	}


}
