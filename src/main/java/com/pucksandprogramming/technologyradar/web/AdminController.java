package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import netscape.security.ForbiddenTargetException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    HttpServletRequest securityContext;

    @GetMapping("/index")
    public ModelAndView index(Model viewModel)
    {
        ModelAndView retVal = new ModelAndView();

        if(securityContext.isUserInRole("ADMIN"))
        {
            retVal.setViewName("admin/index");

            RadarUser currentUser = this.getCurrentUser();
            if (currentUser != null)
            {
                retVal.addObject("userId", currentUser.getId());
            } else
            {
                retVal.addObject("userId", -1);
            }
        }
        else
        {
            throw new ForbiddenTargetException();
        }

        return retVal;
    }
}
