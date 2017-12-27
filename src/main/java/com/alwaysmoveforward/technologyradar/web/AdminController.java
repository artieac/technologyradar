package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.security.Auth0TokenAuthentication;
import com.alwaysmoveforward.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private RadarUserService radarUserService;

    @RequestMapping("/index")
    public ModelAndView index(Model viewModel)
    {
        return this.GenerateTechnologyAssessmentsModelAndView();
    }

    @RequestMapping("/TechnologyAssessments")
    public ModelAndView manageTechnologyAssessments()
    {
        return this.GenerateTechnologyAssessmentsModelAndView();
    }

    private ModelAndView GenerateTechnologyAssessmentsModelAndView()
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("/admin/manageTechnologyAssessments");

        RadarUser currentUser = null;
        SecurityContext foo = SecurityContextHolder.getContext();

        Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication)SecurityContextHolder.getContext().getAuthentication();

        if(tokenAuth!=null)
        {
            currentUser = this.radarUserService.findByAuthenticationId(tokenAuth.getIdentifier());

            if(currentUser != null)
            {
                retVal.addObject("userId", currentUser.getId());
            }
            else
            {
                retVal.addObject("userId", -1);
            }
        }
        else
        {
            retVal.addObject("userId", -1);
        }

        return retVal;

    }

}
