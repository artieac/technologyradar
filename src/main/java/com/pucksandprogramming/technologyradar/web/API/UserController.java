package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarTypeViewModel;
import com.pucksandprogramming.technologyradar.web.Models.UserViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    private RadarInstanceServiceFactory radarInstanceServiceFactory;

    @RequestMapping(value = "/User", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody UserViewModel getUserDetails()
    {
        UserViewModel retVal = UserViewModel.DefaultInstance();

        if (this.getCurrentUser() != null)
        {
            retVal = new UserViewModel(this.getCurrentUser());
            retVal.setNumberOfSharedRadar(radarInstanceServiceFactory.getFullHistory().getSharedRadarCount(this.getCurrentUser().getId()));
        }

        return retVal;
    }
}
