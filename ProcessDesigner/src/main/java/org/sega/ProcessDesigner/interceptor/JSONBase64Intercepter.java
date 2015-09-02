package org.sega.ProcessDesigner.interceptor;

import java.io.UnsupportedEncodingException;

import org.sega.ProcessDesigner.actions.ProcessDesignerSupport;
import org.sega.ProcessDesigner.models.Process;
import org.sega.ProcessDesigner.util.Base64Util;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;

public class JSONBase64Intercepter implements Interceptor {

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (actionInvocation.getAction() instanceof ProcessDesignerSupport) {
			ProcessDesignerSupport action = (ProcessDesignerSupport) actionInvocation
					.getAction();
			Process process = action.getProcess();
			if (process != null) {
				
				process.setDatabaseJSON(Base64Util.encode(process
						.getDatabaseJSON()));
				process.setDDmappingJSON(Base64Util.encode(process
						.getDDmappingJSON()));
				process.setEDmappingJSON(Base64Util.encode(process
						.getEDmappingJSON()));
				process.setEntityJSON(Base64Util.encode(process.getEntityJSON()));
				process.setProcessJSON(Base64Util.encode(process
						.getProcessJSON()));
				process.setProcessXML(Base64Util.encode(process.getProcessXML()));
			}
			actionInvocation.addPreResultListener(new PreResultListener() {

				@Override
				public void beforeResult(ActionInvocation actionInvocation,
						String resultCode) {
					ProcessDesignerSupport action = (ProcessDesignerSupport) actionInvocation
							.getAction();
					Process process = action.getProcess();
					try {
						if (process != null) {
							process.setDatabaseJSON(Base64Util.decode(process
									.getDatabaseJSON()));
							process.setDDmappingJSON(Base64Util.decode(process
									.getDDmappingJSON()));
							process.setEDmappingJSON(Base64Util.decode(process
									.getEDmappingJSON()));
							process.setEntityJSON(Base64Util.decode(process
									.getEntityJSON()));
							process.setProcessJSON(Base64Util.decode(process
									.getProcessJSON()));
							process.setProcessXML(Base64Util.decode(process
									.getProcessXML()));
						}
					} catch (UnsupportedEncodingException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return actionInvocation.invoke();
	}

}
