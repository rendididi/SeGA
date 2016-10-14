package org.sega.viewer.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sega.viewer.models.ProcessEdit;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessEditRepository;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@Autowired
	private ProcessInstanceRepository processInstanceRepository;
	@Autowired
	private ProcessEditRepository processEditRepository;
	@Autowired
	private ProcessInstanceService processInstanceService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final int pageSize = 6;

	@RequestMapping(value = "", method = GET)
	public String index(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
		page = page < 0 ? 0 : page;
		Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));

		Map<String, String> taskNameMap = new HashMap<String, String>();

		for(ProcessInstance instance: instances){
			taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
		}
		
		model.addAttribute("instances", instances);
		model.addAttribute("taskNames", taskNameMap);
		model.addAttribute("totalInstances", instances.getTotalElements());
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", instances.getTotalPages());
		return principal != null ? "home/index" : "users/signin";
	}

	@RequestMapping(value = "/completed", method = GET)
	public String indexCompleted(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
		page = page < 0 ? 0 : page;
		Page<ProcessInstance> instances = processInstanceRepository.findByNextTask(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));

		Map<String, String> taskNameMap = new HashMap<String, String>();

		for(ProcessInstance instance: instances){
			taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
		}

		model.addAttribute("instances", instances);
		model.addAttribute("taskNames", taskNameMap);
		model.addAttribute("totalInstances", instances.getTotalElements());
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", instances.getTotalPages());
		return principal != null ? "home/completed" : "users/signin";
	}
	
	//selection by wxf
    @SuppressWarnings("null")
	@RequestMapping(value = "/type/{processType:\\d+}",method=RequestMethod.GET)
    public String selectTask(@PathVariable String processType,Principal principal, @RequestParam(defaultValue = "0") int page, Model model){
    	page = page < 0 ? 0 : page;
    	Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    	String city = "hz";
    	List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
    	try{
    		List<ProcessEdit> edits = processEditRepository.findByTypeAndCity(processType,city);
    		logger.debug(edits.size()+"==============================================");
    		if(edits != null && instances != null && edits.size() != 0 && instances.getSize() != 0){
    			logger.debug("first-step");
        		for(ProcessInstance instance : instances){
            		for(ProcessEdit edit : edits){
            			logger.debug(instance.getProcess().getId() + ","+edit.getProcess().getId());
            			if(instance.getProcess().getId() == edit.getProcess().getId()){
            				logger.debug("second-step");
            				newInstances.add(instance);
            			}
            		}
            	}
        	}
    		logger.debug("third-step");
    		//logger.debug(newInstances.size()+"++++++++++++++++++++++++++++++++++++++");
    	}catch(Exception e){
    		logger.debug("111111111111111111111111"+e.getMessage()+"111111111111111111111");
    	}
    	Map<String, String> taskNameMap = new HashMap<String, String>();

		for(ProcessInstance instance: newInstances){
			taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
		}
		model.addAttribute("processType",processType);
		model.addAttribute("instances", newInstances);
		model.addAttribute("taskNames", taskNameMap);
		model.addAttribute("totalInstances", newInstances.size());
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", (newInstances.size())/6);
    	return principal != null ? "home/index" : "users/signin";
    	
    }

}
