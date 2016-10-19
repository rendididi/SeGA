package org.sega.viewer.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.repositories.ProcessRepository;
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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class HomeController {

	@Autowired
	private ProcessInstanceRepository processInstanceRepository;
	@Autowired
	private ProcessRepository processRepository;
	@Autowired
	private ProcessInstanceService processInstanceService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final int pageSize = 6;
	String city = null;
	@RequestMapping(value = "", method = GET)
	public String index(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
		page = page < 0 ? 0 : page;
		Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
		city = getCity();
		logger.debug("zx=========0   HomeController");
		List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
    	if(instances != null && instances.getSize() != 0){
    		for(ProcessInstance instance : instances){
        		if(instance.getProcess().getCity().equals(city)){
        			newInstances.add(instance);
        		}
        	}
    	}
		Map<String, String> taskNameMap = new HashMap<String, String>();
		for(ProcessInstance instance: instances){
			taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
		}
		model.addAttribute("processType",0);
		model.addAttribute("instances", newInstances);
		model.addAttribute("taskNames", taskNameMap);
		model.addAttribute("totalInstances", newInstances.size());
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", newInstances.size() / pageSize);
		return principal != null ? "home/index" : "users/signin";
	}

	@RequestMapping(value = "/completed", method = GET)
	public String indexCompleted(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
		page = page < 0 ? 0 : page;
		Page<ProcessInstance> instances = processInstanceRepository.findByNextTask(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
		city = getCity();
		List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
    	if(instances != null && instances.getSize() != 0){
    		for(ProcessInstance instance : instances){
        		if(instance.getProcess().getCity().equals(city)){
        			newInstances.add(instance);
        		}
        	}
    	}
		Map<String, String> taskNameMap = new HashMap<String, String>();

		for(ProcessInstance instance: instances){
			taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
		}
		model.addAttribute("processType",0);
		model.addAttribute("instances", newInstances);
		model.addAttribute("taskNames", taskNameMap);
		model.addAttribute("totalInstances", newInstances.size());
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", newInstances.size() /pageSize);
		return principal != null ? "home/completed" : "users/signin";
	}
	
	//selection by wxf
    @SuppressWarnings("null")
	@RequestMapping(value = "/type/{processType:\\d+}",method=RequestMethod.GET)
    public String selectTask(@PathVariable String processType,Principal principal, @RequestParam(defaultValue = "0") int page, Model model){
    	page = page < 0 ? 0 : page;
    	city = getCity();
    	Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    	List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
    	if(instances != null && instances.getSize() != 0){
    		for(ProcessInstance instance : instances){
        		if(instance.getProcess().getCity().equals(city) && instance.getProcess().getType().equals(processType)){
        			newInstances.add(instance);
        		}
        	}
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
		model.addAttribute("totalPages", (newInstances.size())/pageSize);
    	return principal != null ? "home/index" : "users/signin";
    }
    
    public String getCity(){
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
        String city = (String) request.getSession().getAttribute("city");
        return city;
    }
    
}
