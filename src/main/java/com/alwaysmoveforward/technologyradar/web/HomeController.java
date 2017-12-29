package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/14/2016.
 */

@Controller
public class HomeController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping("/home/index")
    public String index(Model viewModel)
    {
        viewModel.addAttribute("message", "hello");
        return "/home/index";
    }

    @RequestMapping(value = { "/home/secureradar", "/home/secureradar/{assessmentId}" })
    public ModelAndView secureRadar(@PathVariable Optional<Long> assessmentId)
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

        if(assessmentId.isPresent())
        {
            modelAndView.addObject("assessmentId", assessmentId.get());
        }

        modelAndView.setViewName("/home/radar");
        return modelAndView;
    }

    // I hate this url format, but I can't figure out how to get seccurity working with the
    // format that I want
    @RequestMapping(value = { "/", "/home/radar/{userId}", "/home/radar/{userId}/{assessmentId}" })
    public ModelAndView publicRadar(@PathVariable Long userId, @PathVariable Optional<Long> assessmentId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        if(assessmentId.isPresent())
        {
            modelAndView.addObject("assessmentId", assessmentId.get());
        }

        modelAndView.setViewName("/home/radar");
        return modelAndView;
    }
}
