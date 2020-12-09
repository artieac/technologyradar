package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestScope
@Secured("ROLE_ADMIN")
@RequestMapping("/api")
public class RoleController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RoleController.class);

    @GetMapping(value = "/roles", produces = "application/json")
    public @ResponseBody List<Role> getRoles() {
        List<Role> retVal = new ArrayList<>();

        retVal.add(Role.createUserRole());
        retVal.add(Role.createAdminRole());

        return retVal;
    }
}