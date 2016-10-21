package org.sega.viewer.controllers;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.EdbService;
import org.sega.viewer.services.JtangEngineService;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.support.TasksResolver;
import org.sega.viewer.services.support.UnSupportEdbException;
import org.sega.viewer.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("processes/instances")
public class InstanceController {
    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private JtangEngineService jtangEngineService;

    @Autowired
    private EdbService edbService;

    private static final String TASK_TEMPLATE = "templates/fragments/humantask/%s/%s.html";

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @RequestMapping(value = "{instanceId:\\d+}/task/{taskId}", method = RequestMethod.GET)
    public String showTask(@PathVariable Long instanceId, @PathVariable String taskId, Model model)
            throws UnsupportedEncodingException {
    	logger.debug("zhi xing le ma "+taskId);
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        String path = String.format(TASK_TEMPLATE, instance.getProcess().getId(), taskId);

        logger.debug("Resolved task template file: " + path);

        JSONObject entity = processInstanceService.readEntity(instance, taskId);

        model.addAttribute("entity", entity.toString());
        model.addAttribute("schema", Base64Util.decode(instance.getProcess().getEntityJSON()));
        model.addAttribute("instance", instance);
        model.addAttribute("templatePath", path);
        model.addAttribute("taskId", taskId);
        model.addAttribute("taskName",processInstanceService.getNextTaskName(instance));
        model.addAttribute("templateHtml", readTaskTemplate(path));

        return "instances/task";
    }

    // edb sync test
    @RequestMapping(value = "{instanceId:\\d+}/sync_edb", method = RequestMethod.GET)
    @ResponseBody
    public String syncTest(@PathVariable Long instanceId) throws ClassNotFoundException, SQLException, UnSupportEdbException, UnsupportedEncodingException {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);

        edbService.sync(instance);

        return "";
    }

    @RequestMapping(
            value = "{instanceId:\\d+}/task/{taskId}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String commitTask(@PathVariable Long instanceId, @PathVariable String taskId, @RequestBody String entity)
            throws UnsupportedEncodingException, MalformedURLException {
    	 logger.debug("这是不是也调用了ppppppppppppppppp");
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        processInstanceService.writeEntity(new JSONObject(entity), instance, taskId);

        try { 
            TasksResolver tasksResolver = new TasksResolver(instance.getProcess().getBindingJson(), instance.getProcess().getProcessJSON());
            
            //Commit to JTang Server
            //DEMO:String nextTask = jtangEngineService.commitTask(instance);
            String nextTask = tasksResolver.getNextTask(taskId);
            
            //persist instance
            
            instance.setNextTask(nextTask);
            processInstanceService.updateInstance(instance);

            //Sync to EDB
            if (tasksResolver.getTask(taskId).isSyncPoint()) {
                //DEMO:edbService.sync(instance);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "{\"success\": false}";
        }

        return "{\"success\": true}";
    }

    /**
     * Read task template file
     *
     * @param file template file path
     * @return raw content of file
     */
    private String readTaskTemplate(String file) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(file), "UTF-8");
        } catch (IOException e) {
            logger.error("Failed to read task template file " + file + ", got exception: " + e);
        }
        return result;
    }


}
