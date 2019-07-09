package com.pucksandprogramming.technologyradar.web.Public;

import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller("PublicRadarSubjectController")
public class RadarSubjectController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarSubjectController.class);

    @Autowired
    private RadarServiceFactory radarServiceFactory;

    @Autowired
    private TechnologyService technologyService;

    @GetMapping(value ={"/public/radarsubject/search"})
    public ModelAndView technologySearch(ModelAndView model)
    {
        model.setViewName("radarsubject/search");

        return model;
    }

    @GetMapping(value={"/public/radarsubject/{id}"})
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.technologyService.findById(id);
        model.addObject("targetTechnology", targetTechnology);
        model.setViewName("radarsubject/details");
        return model;
    }
}
