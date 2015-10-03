package org.sega.viewer.controllers;

import org.apache.commons.io.IOUtils;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

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

    @RequestMapping("{instanceId:\\d+}/task/{taskId}")
    public String showTask(@PathVariable Long instanceId, @PathVariable String taskId, Model model) {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        String path = String.format(TASK_TEMPLATE, instance.getProcess().getId(), taskId);

        logger.debug("Resolved task template file: " + path);

        model.addAttribute("instance", instance);
        model.addAttribute("templatePath", path);
        model.addAttribute("taskId", taskId);
        model.addAttribute("templateHtml", readTemplateFile(path));

        return "instances/task";
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
