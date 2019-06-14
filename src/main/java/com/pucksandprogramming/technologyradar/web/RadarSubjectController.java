package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import com.pucksandprogramming.technologyradar.web.Models.RadarSubjectBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by acorrea on 12/28/2017.
 */
@SuppressWarnings("unused")
@Controller
public class RadarSubjectController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarSubjectController.class);

    @Autowired
    private RadarInstanceService radarInstanceService;

    @RequestMapping("/radarsubject/search")
    public ModelAndView technologySearch(ModelAndView model)
    {
        model.setViewName("radarsubject/search");

        if(this.getCurrentUser()!=null)
        {
            model.addObject("userId", this.getCurrentUser().getId());
        }
        return model;
    }

    @RequestMapping("/radarsubject/{id}")
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.radarInstanceService.findTechnologyById(id);
        List<Radar> radarList = radarInstanceService.getAllByTechnologyId(id);

        RadarSubjectBreakdown viewModel = new RadarSubjectBreakdown(targetTechnology);

        for(int i = 0; i < radarList.size(); i++)
        {
            viewModel.addRadarSubjectAssessment(radarList.get(i), this.getCurrentUser());
        }

        model.setViewName("radarsubject/details");
        model.addObject("targetTechnology", targetTechnology);
        model.addObject("assessmentItems", radarList);

        if(this.getCurrentUser()!=null)
        {
            model.addObject("userId", this.getCurrentUser().getId());
        }

        return model;
    }
}
