package org.sega.viewer.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

	/**
	 * Handle exceptions thrown by handlers.
	 */

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView exception(HttpServletRequest request, Exception exception) {
		ModelAndView modelAndView = new ModelAndView("error/general");
		modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
		return modelAndView;
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public RedirectView csrf_exception(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
		RedirectView  view = new RedirectView ("/signin");
		view.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return view;
	}


}