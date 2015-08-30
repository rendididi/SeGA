/**
 * 
 */
package org.sega.ProcessDesigner.interceptor;

import java.util.Map;

import org.sega.ProcessDesigner.actions.ProcessDesignerSupport;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author glp
 *
 */
public class AuthenticationInterceptor implements Interceptor  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1593018216709817247L;

	@Override
	public void destroy() {	}

	@Override
	public void init() { }

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

        Map session = actionInvocation.getInvocationContext().getSession();

        String user = (String) session.get("userType");
        boolean isAuthenticated = ("expert".equals(user) || "developer".equals(user));

        if (!isAuthenticated) {
            return Action.LOGIN;            
        }
        else {
        	if(actionInvocation.getAction() instanceof ProcessDesignerSupport){
        		ProcessDesignerSupport action = (ProcessDesignerSupport)actionInvocation.getAction();
        		action.setUserType(user);
        	}
            return actionInvocation.invoke();
        }
	}

}
