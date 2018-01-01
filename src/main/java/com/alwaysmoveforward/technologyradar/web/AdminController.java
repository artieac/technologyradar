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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.sound.midi.ControllerEventListener;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private RadarUserService radarUserService;

    @RequestMapping("/index")
    public ModelAndView index(Model viewModel)
    {
        return this.GenerateRadarInstanceModelAndView();
    }

    @RequestMapping("/Radars")
    public ModelAndView manageRadars()
    {
        return this.GenerateRadarInstanceModelAndView();
    }

    private ModelAndView GenerateRadarInstanceModelAndView()
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("/admin/manageRadars");

        RadarUser currentUser = this.getCurrentUser();
        if(currentUser != null)
        {
            retVal.addObject("userId", currentUser.getId());
        }
        else
        {
            retVal.addObject("userId", -1);
        }

        return retVal;
    }

    @RequestMapping("/Radar/{radarId}")
    public ModelAndView manageRadarItems(@PathVariable Long radarId)
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("/admin/manageRadarItems");
        retVal.addObject("radarId", radarId);

        RadarUser currentUser = this.getCurrentUser();
        if(currentUser != null)
        {
            retVal.addObject("userId", currentUser.getId());
        }
        else
        {
            retVal.addObject("userId", -1);
        }

        return retVal;
    }


}
