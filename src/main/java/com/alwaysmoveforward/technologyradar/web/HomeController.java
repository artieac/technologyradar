package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by acorrea on 10/14/2016.
 */

@Controller
public class HomeController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping( value = {"/", "/public/home/index"})
    public String index(Model viewModel)
    {
        viewModel.addAttribute("message", "hello");
        return "home/index";
    }

    @RequestMapping(value = { "/home/secureradar", "/home/secureradar/{radarInstanceId}" })
    public ModelAndView secureRadar(@PathVariable Optional<Long> radarInstanceId)
    {
        ModelAndView modelAndView = new ModelAndView();
        RadarUser currentUser = this.getCurrentUser();

        if(currentUser != null)
        {
            modelAndView.addObject("userId", currentUser.getId());
        }
        else
        {
            modelAndView.addObject("userId", 2);
        }

        if(radarInstanceId.isPresent())
        {
            modelAndView.addObject("radarInstanceId", radarInstanceId.get());
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    // I hate this url format, but I can't figure out how to get seccurity working with the
    // format that I want
    @RequestMapping(value = { "/public/home/radars/{userId}", "/public/home/radar/{userId}/{radarInstanceId}" })
    public ModelAndView publicRadar(@PathVariable Long userId, @PathVariable Optional<Long> radarInstanceId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        if(radarInstanceId.isPresent())
        {
            modelAndView.addObject("radarInstanceId", radarInstanceId.get());
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }
}
