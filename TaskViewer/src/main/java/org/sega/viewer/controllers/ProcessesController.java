package org.sega.viewer.controllers;

import org.sega.viewer.models.*;
import org.sega.viewer.models.Process;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.JtangEngineService;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.ProcessService;
import org.sega.viewer.services.support.Node;
import org.sega.viewer.services.support.ProcessJsonResolver;
import org.sega.viewer.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Raysmond<i@raysmond.com>.
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

    @Autowired
    private JtangEngineService jtangEngineService;

    @RequestMapping(value = "", method = GET)
    public String processes(Model model){
        List<Process> processes = processService.getAllProcesses();
        model.addAttribute("processes", processes);
        model.addAttribute("totalProcesses", processes.size());

        return "processes/index";
    }

    @RequestMapping(value = "{processId:\\d+}", method = GET)
    public String showProcess(@PathVariable Long processId, Model model){
        Process process = processService.getProcess(processId);

        model.addAttribute("process", process);
        model.addAttribute("instances", processInstanceService.getProcessInstances(process));
        model.addAttribute("processJson", decodeJson(process.getProcessJSON()));
        model.addAttribute("processXML", decodeJson(process.getProcessXML()));
        model.addAttribute("entityJson", decodeJson(process.getEntityJSON()));
        model.addAttribute("bindingResultJson", decodeJson(process.getBindingJson()));
        model.addAttribute("edMappingJson", decodeJson(process.getEDmappingJSON()));
        model.addAttribute("databaseJson", decodeJson(process.getDatabaseJSON()));
        model.addAttribute("ddMappingJson", decodeJson(process.getDDmappingJSON()));

        return "processes/show";
    }

    @RequestMapping(value = "{processId:\\d+}", method = POST)
    public String createProcessInstance(@PathVariable Long processId) throws UnsupportedEncodingException, MalformedURLException {
        Process process = processService.getProcess(processId);
        ProcessInstance instance = processInstanceService.createProcessInstance(process);

        jtangEngineService.publishProcess(instance);

        return "redirect:instances/" + instance.getId();
    }

    @RequestMapping(value = "instances/{instanceId:\\d+}", method = GET)
    public String showProcessInstance(@PathVariable Long instanceId, Model model) throws UnsupportedEncodingException {
        ProcessInstance processInstance = processInstanceRepository.findOne(instanceId);
        model.addAttribute("instance", processInstance);
        model.addAttribute("processJSON", Base64Util.decode(processInstance.getProcess().getProcessJSON()));

        ProcessJsonResolver processJsonResolver = new ProcessJsonResolver(processInstance.getProcess().getProcessJSON());
        Node node = processJsonResolver.findNode(processInstance.getNextTask());
        model.addAttribute("nextTask", node);
        String page = "";
        if(node.getType().equals("sega.Task")){
            page = "instances/show";
        }else if(node.getType().equals("sega.Service")){
            page = "instances/show_service";
        }
        return page;
    }

    @RequestMapping(value = "{processId:\\d+}/templates/{taskId}", method = GET)
    public ModelAndView getTaskTemplate(@PathVariable Long processId, @PathVariable String taskId){
        ModelAndView template = new ModelAndView("fragments/humantask/"+processId+"/"+taskId);
        return template;
    }

    private String decodeJson(String json){
        String result = null;
        try {
            result = Base64Util.decode(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
