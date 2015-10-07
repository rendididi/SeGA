package org.sega.viewer.controllers;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.support.Node;
import org.sega.viewer.services.support.ProcessJsonResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Controller
@RequestMapping("processes/instances")
public class InstanceController {
    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    private static final String TASK_TEMPLATE = "templates/fragments/humantask/%s/%s.html";

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @RequestMapping(value = "{instanceId:\\d+}/task/{taskId}", method = RequestMethod.GET)
    public String showTask(@PathVariable Long instanceId, @PathVariable String taskId, Model model) {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        String path = String.format(TASK_TEMPLATE, instance.getProcess().getId(), taskId);

        logger.debug("Resolved task template file: " + path);

        // TODO
        // JSONObject entity = processInstanceService.readEntity(instance, taskId);

        JSONObject entity = new JSONObject()
                .put("j1_4", 148245)
                .put("j1_5", new JSONObject().put("j1_6", 1001).put("j1_7","Raysmond"));

        model.addAttribute("entity", entity.toString());
        model.addAttribute("instance", instance);
        model.addAttribute("templatePath", path);
        model.addAttribute("taskId", taskId);
        model.addAttribute("templateHtml", readTemplateFile(path));

        return "instances/task";
    }

    @RequestMapping(value = "{instanceId:\\d+}/task/{taskId}", method = RequestMethod.POST, headers = "Accept=application/json")
    public String commitTask(@PathVariable Long instanceId, @PathVariable String taskId, @RequestBody String entity, Model model) throws UnsupportedEncodingException {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);

//        JSONObject input = new JSONObject(entity);

        // TODO
//        JSONObject entity = processInstanceService.writeEntity(instance, taskId);

//        ProcessJsonResolver processJsonResolver = new ProcessJsonResolver(instance.getProcess().getProcessJSON());
//        Node nextTask = processJsonResolver.getNextTask(taskId);
//        instance.setNextTask(nextTask.getId());

        return "redirect:/processes/instances/" + instanceId;
    }

    private String readTemplateFile(String fileName) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            logger.error("Failed to read task template file " + fileName + ", got exception: " + e);
        }

        return result;
    }
}
