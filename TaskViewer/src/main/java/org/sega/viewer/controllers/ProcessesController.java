package org.sega.viewer.controllers;

import org.sega.viewer.models.*;
import org.sega.viewer.models.Process;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Controller
@RequestMapping(value = "processes")
public class ProcessesController {
    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @RequestMapping(value = "", method = GET)
    public String processes(Model model){
        List<Process> processes = processService.getAllProcesses();
        model.addAttribute("processes", processes);

        return "processes/index";
    }

    @RequestMapping(value = "{processId:\\d+}", method = POST)
    public String createProcessInstance(@PathVariable Long processId){
        Process process = processService.getProcess(processId);
        ProcessInstance instance = processInstanceService.createProcessInstance(process);

        return "redirect:instances/" + instance.getId();
    }

    @RequestMapping(value = "instances/{instanceId:\\d+}", method = GET)
    public String showProcessInstance(@PathVariable Long instanceId, Model model){
        ProcessInstance processInstance = processInstanceRepository.findOne(instanceId);
        model.addAttribute("instance", processInstance);

        return "processes/show";
    }

    @RequestMapping(value = "{processId:\\d+}/templates/{taskId}", method = GET)
    public ModelAndView getTaskTemplate(@PathVariable Long processId, @PathVariable String taskId){
        ModelAndView template = new ModelAndView("fragments/humantask/"+processId+"/"+taskId);
        return template;
    }
}
