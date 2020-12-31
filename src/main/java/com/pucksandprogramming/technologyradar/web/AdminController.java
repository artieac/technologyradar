package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestScope
@ControllerAdvice
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(AdminController.class);

    private final RadarUserService radarUserService;
    private final HttpServletRequest securityContext;

    @Autowired
    public AdminController(RadarUserService radarUserService,
                           HttpServletRequest securityContext){
        this.radarUserService = radarUserService;
        this.securityContext = securityContext;
    }

    @GetMapping("/index")
    public ModelAndView index(Model viewModel) {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("admin/index");
        retVal.addObject("userId", this.getCurrentUserId());
        return retVal;
    }
}
