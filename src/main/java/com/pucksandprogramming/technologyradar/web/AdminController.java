package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private RadarUserService radarUserService;

    @RequestMapping("/index")
    public ModelAndView index(Model viewModel)
    {
        return this.GenerateRadarInstanceModelAndView("admin/adminIndex");
    }

    @RequestMapping("/Radars")
    public ModelAndView manageRadars()
    {
        return this.GenerateRadarInstanceModelAndView("admin/manageRadars");
    }

    private ModelAndView GenerateRadarInstanceModelAndView(String viewName)
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName(viewName);

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

    @RequestMapping("/UserAccess")
    public ModelAndView manageUserAccess()
    {
        return this.GenerateRadarInstanceModelAndView("admin/manageUserAccess");
    }

    @RequestMapping("/Radar/{radarId}")
    public ModelAndView manageRadarItems(@PathVariable Long radarId)
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("admin/manageRadarItems");
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

    @RequestMapping("/Radar/{radarId}/addFromPrevious")
    public ModelAndView addItemsFromPrevious(@PathVariable Long radarId)
    {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("admin/addFromPrevious");
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
