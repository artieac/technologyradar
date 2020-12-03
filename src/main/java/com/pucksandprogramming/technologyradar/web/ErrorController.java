package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by acorrea on 1/11/2018.
 */
@Controller
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final Logger logger = Logger.getLogger(ErrorController.class);

    private AuthenticatedUser authenticatedUser = null;

    public AuthenticatedUser getAuthenticatedUser() {
        if(this.authenticatedUser == null) {
            if(SecurityContextHolder.getContext().getAuthentication() instanceof Auth0TokenAuthentication) {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null) {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
            }
        }

        return this.authenticatedUser;
    }

    @Override
    public String getErrorPath() { return "/error";}

    @RequestMapping(value = { "/error", "/error/default"})
    public String defaultErrorPage(final HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/internalservererror";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value() ||
                    statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/accessDenied";
            }
        }

        return "error/default";
    }

    @RequestMapping(value = "/error/accessdenied", method = RequestMethod.GET)
    public ModelAndView accessDenied(final HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("unauthorized", "true");
        modelAndView.setViewName("error/accessdenied");
        return modelAndView;
    }
}
