package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
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
@Secured("ROLE_ADMIN")
@RequestMapping("/api")
public class RoleController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RoleController.class);

    @GetMapping(value = "/roles", produces = "application/json")
    public @ResponseBody List<Role> getRoles()
    {
        List<Role> retVal = new ArrayList<>();

        retVal.add(Role.createUserRole());
        retVal.add(Role.createAdminRole());

        return retVal;
    }
}