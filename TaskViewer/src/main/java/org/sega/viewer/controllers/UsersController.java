package org.sega.viewer.controllers;

import org.sega.viewer.forms.SigninForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class UsersController {
    private static final String VIEW_SIGNIN = "users/signin";

    @RequestMapping(value = "signin", method = GET)
    public String signin(Model model) {
        model.addAttribute(new SigninForm());

        return VIEW_SIGNIN;
    }
}
