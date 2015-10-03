package org.sega.viewer.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class HomeController {

	@RequestMapping(value = "", method = GET)
	public String index(Principal principal) {

		return principal != null ? "home/index" : "users/signin";
	}

}
