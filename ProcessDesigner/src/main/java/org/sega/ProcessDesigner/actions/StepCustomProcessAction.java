package org.sega.ProcessDesigner.actions;

import java.io.File;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.sega.ProcessDesigner.models.Process;

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
		
		FileUtils.writeStringToFile(file, svg);
		sp.setProcessImageUrl(random_name);
	}
	
	private String svg;

	public String getSvg() {
		return svg;
	}

	public void setSvg(String svg) {
		this.svg = svg;
	}


}
