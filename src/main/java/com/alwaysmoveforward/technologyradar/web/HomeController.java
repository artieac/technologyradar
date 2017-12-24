package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.security.TokenAuthentication;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * Created by acorrea on 10/14/2016.
 */

@Controller
public class HomeController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private TechnologyAssessmentService technologyAssessmentService;

    @RequestMapping("/home/index")
    public String index(Model viewModel)
    {
        viewModel.addAttribute("message", "hello");
        return "/home/index";
    }

    @RequestMapping(value = { "/", "/home/secureradar" })
    public ModelAndView secureRadar(final Principal principal)
    {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("userId", principal);
        modelAndView.setViewName("/home/secureRadar");
        return modelAndView;
    }

    @RequestMapping(value = { "/", "/home/radar" })
    public ModelAndView publicRadar(final Principal principal)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", principal);
        modelAndView.setViewName("/home/publicRadar");
        return modelAndView;
    }

    @RequestMapping("/technology")
    public String technologySearch()
    {
        return "technologySearch";
    }

    @RequestMapping("/technology/{id}")
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.technologyAssessmentService.findTechnologyById(id);
        List<TechnologyAssessment> technologyAssessments = technologyAssessmentService.getTechnologyAssessmentsByTechnologyId(id);

        TechnologyBreakdown viewModel = new TechnologyBreakdown(targetTechnology);

        for(int i = 0; i < technologyAssessments.size(); i++)
        {
            viewModel.addTechnologyAssessment(technologyAssessments.get(i));
        }

        model.setViewName("technology");
        model.addObject("targetTechnology", targetTechnology);
        model.addObject("assessmentItems", technologyAssessments);
        return model;
    }
}
