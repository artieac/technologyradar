package com.pucksandprogramming.technologyradar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by acorrea on 1/11/2018.
 */
@Controller
public class AccessDeniedController
{
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied(final HttpServletRequest req)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("unauthorized", "true");
        modelAndView.setViewName("home/index");
        return modelAndView;
    }
}
