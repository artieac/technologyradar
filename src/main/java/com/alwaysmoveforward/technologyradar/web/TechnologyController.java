package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.Radar;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.services.RadarService;
import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
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
public class TechnologyController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(TechnologyController.class);

    @Autowired
    private RadarService radarService;

    @RequestMapping("/technology/search")
    public String technologySearch()
    {
        return "technology/search";
    }

    @RequestMapping("/technology/{id}")
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.radarService.findTechnologyById(id);
        List<Radar> radarList = radarService.getAllByTechnologyId(id);

        TechnologyBreakdown viewModel = new TechnologyBreakdown(targetTechnology);

        for(int i = 0; i < radarList.size(); i++)
        {
            viewModel.addTechnologyAssessment(radarList.get(i), this.getCurrentUser());
        }

        model.setViewName("technology/details");
        model.addObject("targetTechnology", targetTechnology);
        model.addObject("assessmentItems", radarList);
        return model;
    }
}
