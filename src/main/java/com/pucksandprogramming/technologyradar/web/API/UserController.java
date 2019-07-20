package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.UserViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    private RadarService radarService;

    @GetMapping(value = "/User", produces = "application/json")
    public @ResponseBody UserViewModel getUserDetails()
    {
        UserViewModel retVal = UserViewModel.DefaultInstance();

        try
        {
            if (this.getCurrentUser() != null)
            {
                retVal = new UserViewModel(this.getCurrentUser());
                retVal.setNumberOfSharedRadar(this.radarService.getSharedRadarCount(this.getCurrentUser().getId()));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/Users", produces = "application/json")
    public @ResponseBody List<UserViewModel> getAllUsers()
    {
        List<UserViewModel> retVal = new ArrayList<>();

        if(this.getCurrentUser() != null)
        {
            Role userRole = Role.createRole(this.getCurrentUser().getRoleId());

            if(userRole.getId()==Role.RoleType_Admin)
            {
                List<RadarUser> radarUsers = this.radarUserService.getAllUsers(this.getCurrentUser());

                for(RadarUser radarUser : radarUsers)
                {
                    retVal.add(new UserViewModel(radarUser));
                }
            }
        }

        return retVal;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/User/:userId", produces = "application/json")
    public @ResponseBody UserViewModel getUserById(@PathVariable Long userId)
    {
        UserViewModel retVal = null;

        if(this.getCurrentUser() != null)
        {
            Role userRole = Role.createRole(this.getCurrentUser().getRoleId());

            if(userRole.getId()==Role.RoleType_Admin)
            {
                RadarUser radarUser = this.radarUserService.findOne(userId);

                if(radarUser != null)
                {
                    retVal = new UserViewModel(radarUser);
                }
            }
        }

        return retVal;
    }
}
