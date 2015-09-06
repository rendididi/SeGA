package org.sega.viewer.controllers;

import org.sega.viewer.common.Constants;
import org.sega.viewer.services.IModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UsersController {
    private static final String VIEW_SIGNIN = "users/signin";

    @Autowired
    private IModel model;

    @RequestMapping(value = "signin")
    public String signin(Model form) {
        form.addAttribute(new SigninForm());
        return VIEW_SIGNIN;
    }

    // not used currently
    // Signin authentication is provided by Spring Security module
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public String doSignin(
            HttpServletRequest request,
            @Valid @ModelAttribute SigninForm form,
            Errors errors,
            RedirectAttributes ra) {

        if (errors.hasErrors()) {
            return VIEW_SIGNIN;
        }

        if (form.getUserType().equals("operator")) {
            request.getSession().setAttribute(Constants.KEY_USER_TYPE, Constants.UserType.OPERATOR);
        } else {
            return VIEW_SIGNIN;
        }

        return "redirect:/";
    }
}
