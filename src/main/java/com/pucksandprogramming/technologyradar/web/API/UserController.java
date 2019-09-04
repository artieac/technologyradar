package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
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
    private static final Logger logger = Logger.getLogger(UserController.class);

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

    @GetMapping(value = "/public/User/{userId}", produces = "application/json")
    public @ResponseBody UserViewModel getIdentifiedUserDetils(@PathVariable Long userId)
    {
        UserViewModel retVal = UserViewModel.DefaultInstance();

        try
        {
            boolean shouldRestrict = true;

            retVal = new UserViewModel(this.radarUserService.findOne(userId));

            if (this.getCurrentUser() != null)
            {
                if((userId.compareTo(this.getCurrentUser().getId())==0) ||
                    this.getCurrentUser().getRoleId()==Role.RoleType_Admin)
                {
                    shouldRestrict = false;
                }
            }

            if(shouldRestrict==true)
            {
                if(retVal.getName().isEmpty())
                {
                    retVal.setName(retVal.getNickname());
                }

                retVal.setCanHaveVariableRadarRingCount(false);
                retVal.setNumberOfSharedRadar(0);
                retVal.setRole(Role.createRole(Role.RoleType_User));
                retVal.setUserType(UserType.DefaultInstance());
                retVal.setHowManyRadarsCanShare(0);
                retVal.setAllowTeamMembersToManageRadars(false);
                retVal.setCanHaveNAssociatedRadarTemplates(0);
                retVal.setCanHaveNRadarTemplates(0);
                retVal.setCanSeeFullView(false);
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
    public @ResponseBody List<UserViewModel> getAllUsers(@RequestParam(name = "emailSearch", required = false, defaultValue = "") String emailSearch)
    {
        List<UserViewModel> retVal = new ArrayList<>();

        if(this.getCurrentUser() != null)
        {
            Role userRole = Role.createRole(this.getCurrentUser().getRoleId());

            if(userRole.getId()==Role.RoleType_Admin)
            {
                List<RadarUser> radarUsers = new ArrayList<>();

                if(emailSearch!=null && !emailSearch.isEmpty())
                {
                    radarUsers = this.radarUserService.searchByEmail(emailSearch, this.getCurrentUser());
                }
                else
                {
                    radarUsers = this.radarUserService.getAllUsers(this.getCurrentUser());
                }

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
