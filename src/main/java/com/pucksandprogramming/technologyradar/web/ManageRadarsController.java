package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
@RequestMapping("/manageradars")
public class ManageRadarsController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(ManageRadarsController.class);

    @RequestMapping("/index")
    public ModelAndView index(Model viewModel) {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("manageradars/index");

        RadarUser currentUser = this.getCurrentUser();
        if(currentUser != null) {
            retVal.addObject("userId", currentUser.getId());
        }
        else {
            retVal.addObject("userId", -1);
        }

        return retVal;
    }
}
