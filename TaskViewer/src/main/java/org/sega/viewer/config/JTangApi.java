package org.sega.viewer.config;

import com.caucho.hessian.client.HessianProxyFactory;
import org.SeGA.api.instance.ProcessInsAPI;
import org.SeGA.api.instance.WorkitemAPI;
import org.SeGA.api.schema.ProcessAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Configuration
public class JTangApi {
    @Value("${jtang.url}")
    private String url;

    private static final String FORMAT_PROCESS_API = "%s/ProcessAPI";
    private static final String FORMAT_INSTANCE_API = "%s/ProcessInsAPI";
    private static final String FORMAT_TASK_API = "%s/WorkitemAPI";

    private ProcessInsAPI instanceApi;
    private ProcessAPI processAPI;
    private WorkitemAPI taskApi;

    @PostConstruct
    private void init() throws MalformedURLException {
        HessianProxyFactory factory = new HessianProxyFactory();
        this.instanceApi = (ProcessInsAPI) factory.create(ProcessInsAPI.class, String.format(FORMAT_INSTANCE_API, url));
        this.processAPI = (ProcessAPI) factory.create(ProcessAPI.class, String.format(FORMAT_PROCESS_API, url));
        this.taskApi = (WorkitemAPI) factory.create(WorkitemAPI.class, String.format(FORMAT_TASK_API, url));
    }

    public ProcessInsAPI getInstanceApi() {
        return instanceApi;
    }

    public ProcessAPI getProcessAPI() {
        return processAPI;
    }

    public WorkitemAPI getTaskApi() {
        return taskApi;
    }
}