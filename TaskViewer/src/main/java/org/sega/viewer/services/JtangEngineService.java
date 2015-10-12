package org.sega.viewer.services;

import com.caucho.hessian.client.HessianProxyFactory;
import org.SeGA.api.instance.ProcessInsAPI;
import org.SeGA.api.instance.WorkitemAPI;
import org.SeGA.api.schema.ProcessAPI;
import org.SeGA.model.EngineType;
import org.SeGA.model.JTangProcIns;
import org.SeGA.model.SchemaType;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.ProcessInstanceJTangInfo;
import org.sega.viewer.repositories.ProcessInstanceJTangInfoRepository;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Service
public class JtangEngineService {
    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private ProcessInstanceJTangInfoRepository processInstanceJTangInfoRepository;

    @Autowired
    private JTangEngineConfig jTangEngineConfig;

    public String commitTask(ProcessInstance processInstance) throws MalformedURLException {
        String pubUrl = jTangEngineConfig.getUrl();
        String procUrl = pubUrl + "/ProcessAPI";
        String procInsUrl = pubUrl + "/ProcessInsAPI";
        String workitemUrl = pubUrl + "/WorkitemAPI";

        HessianProxyFactory factory = new HessianProxyFactory();
        ProcessInsAPI procInsAPI = (ProcessInsAPI) factory.create(ProcessInsAPI.class, procInsUrl);
        WorkitemAPI workitemAPI = (WorkitemAPI) factory.create(WorkitemAPI.class, workitemUrl);
        ProcessAPI processAPI = (ProcessAPI) factory.create(ProcessAPI.class, procUrl);

        String schemaType = SchemaType.SimpleJTang;
        String engineType = EngineType.JTang;

        ProcessInstanceJTangInfo processInstanceJTangInfo = processInstance.getjTangInfo();

        org.SeGA.model.Process segaProces = processInstanceJTangInfo.getJtangProcess();
        org.SeGA.model.JTangProcIns segaInstance = processInstanceJTangInfo.getJtangInstance();

        // TODO build input map from read entity
        segaInstance = (JTangProcIns) workitemAPI.commitWorkitem(engineType, segaProces, segaInstance, "14", segaInstance.getActWorkitems().get(0).getId());

        processInstanceJTangInfo.setJtangInstance(segaInstance);
        processInstanceJTangInfoRepository.save(processInstanceJTangInfo);

        String nextTask = segaInstance.getActWorkitems().get(0).getName();

        // TODO log?


        return nextTask;
    }

    public void publishProcess(ProcessInstance processInstance) throws MalformedURLException, UnsupportedEncodingException {
        String url = jTangEngineConfig.getUrl();

        HessianProxyFactory factory = new HessianProxyFactory();
        ProcessInsAPI procInsAPI = (ProcessInsAPI) factory.create(ProcessInsAPI.class,  url + "/ProcessInsAPI");
        WorkitemAPI workitemAPI = (WorkitemAPI) factory.create(WorkitemAPI.class, url + "/WorkitemAPI");
        ProcessAPI processAPI = (ProcessAPI) factory.create(ProcessAPI.class, url + "/ProcessAPI");

        String processXml = Base64Util.decode(processInstance.getProcess().getProcessXML());

        org.SeGA.model.Process segaProcess = processAPI.publishProcess(SchemaType.SimpleJTang, EngineType.JTang, processXml, processInstance.getProcess().getName());
        org.SeGA.model.JTangProcIns segaInstance = (JTangProcIns) procInsAPI.createInstance(EngineType.JTang, segaProcess, processInstance.getProcess().getName() + "_" + processInstance.getId());

        ProcessInstanceJTangInfo processInstanceJTangInfo = new ProcessInstanceJTangInfo(processInstance, segaProcess, segaInstance);

        processInstanceJTangInfoRepository.save(processInstanceJTangInfo);
    }

}

@Configuration
class JTangEngineConfig {
    @Value("${jtang.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}