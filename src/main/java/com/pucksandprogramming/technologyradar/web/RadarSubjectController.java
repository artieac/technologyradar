package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by acorrea on 12/28/2017.
 */
@SuppressWarnings("unused")
@Controller
public class RadarSubjectController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarSubjectController.class);

    @Autowired
    private RadarServiceFactory radarServiceFactory;

    @Autowired
    private TechnologyService technologyService;

    @RequestMapping(value ={"/radarsubject/search"})
    public ModelAndView technologySearch(ModelAndView model)
    {
        model.addObject("userId", this.getCurrentUser().getId());
        model.setViewName("radarsubject/search");
        return model;
    }

    @RequestMapping(value={"/radarsubject/{id}"})
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.technologyService.findById(id);

        model.setViewName("radarsubject/details");
        model.addObject("targetTechnology", targetTechnology);
        model.addObject("userId", this.getCurrentUser().getId());
        return model;
    }
}
