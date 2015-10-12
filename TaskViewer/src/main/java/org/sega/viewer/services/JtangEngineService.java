package org.sega.viewer.services;

import org.SeGA.model.EngineType;
import org.SeGA.model.JTangProcIns;
import org.SeGA.model.SchemaType;
import org.sega.viewer.config.JTangApi;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.ProcessInstanceJTangInfo;
import org.sega.viewer.repositories.ProcessInstanceJTangInfoRepository;
import org.sega.viewer.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Service
public class JtangEngineService {
    @Autowired
    private ProcessInstanceJTangInfoRepository processInstanceJTangInfoRepository;

    @Autowired
    private JTangApi jTangApi;

    public String commitTask(ProcessInstance instance) throws MalformedURLException {
        ProcessInstanceJTangInfo processInstanceJTangInfo = instance.getjTangInfo();
        JTangProcIns jTangInstance = processInstanceJTangInfo.getJtangInstance();

        // TODO build input map from read entity
        jTangInstance = (JTangProcIns) jTangApi.getTaskApi().commitWorkitem(
                EngineType.JTang,
                processInstanceJTangInfo.getJtangProcess(),
                jTangInstance,
                "14",
                jTangInstance.getActWorkitems().get(0).getId());

        processInstanceJTangInfo.setJtangInstance(jTangInstance);
        processInstanceJTangInfoRepository.save(processInstanceJTangInfo);

        // TODO log?

        String nextTask = jTangInstance.getActWorkitems().get(0).getName();

        return nextTask;
    }

    public void publishProcess(ProcessInstance instance)
            throws MalformedURLException, UnsupportedEncodingException {

        org.SeGA.model.Process jTangProcess = jTangApi.getProcessAPI().publishProcess(
                SchemaType.SimpleJTang,
                EngineType.JTang,
                Base64Util.decode(instance.getProcess().getProcessXML()),
                instance.getProcess().getName());

        JTangProcIns jTangInstance = (JTangProcIns) jTangApi.getInstanceApi().createInstance(
                EngineType.JTang,
                jTangProcess,
                instance.getProcess().getName() + "_" + instance.getId());

        ProcessInstanceJTangInfo info = new ProcessInstanceJTangInfo(instance, jTangProcess, jTangInstance);

        processInstanceJTangInfoRepository.save(info);
    }

}
