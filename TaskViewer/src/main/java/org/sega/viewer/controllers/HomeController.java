package org.sega.viewer.controllers;

import java.security.Principal;

import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class HomeController {

	@Autowired
	private ProcessInstanceRepository processInstanceRepository;

	private static final int pageSize = 5;

	@RequestMapping(value = "", method = GET)
	public String index(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
		page = page < 0 ? 0 : page;
		Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
				ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));

		model.addAttribute("instances", instances);
		model.addAttribute("page", page+1);
		model.addAttribute("totalPages", instances.getTotalPages());
		return principal != null ? "home/index" : "users/signin";
	}

}
