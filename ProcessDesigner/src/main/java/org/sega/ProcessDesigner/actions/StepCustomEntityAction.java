package org.sega.ProcessDesigner.actions;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.config_browser.ConfigurationHelper;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.JSONWriter;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.Session;
import org.sega.ProcessDesigner.data.StepConstant;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.models.ProcessEdit;
import org.sega.ProcessDesigner.models.ProcessTemplate;
import org.sega.ProcessDesigner.util.Base64Util;
import org.sega.ProcessDesigner.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.ConfigurationUtil;
import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;

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
		sp.setEntityJSON(getProcess().getEntityJSON());
	}
	

}
