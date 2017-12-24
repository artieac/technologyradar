package com.alwaysmoveforward.technologyradar.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);


    @RequestMapping("/index")
    public String index(Model viewModel)
    {
        return "adminIndex";
    }
}
