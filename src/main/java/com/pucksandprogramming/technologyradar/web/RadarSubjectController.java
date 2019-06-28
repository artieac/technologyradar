package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import com.pucksandprogramming.technologyradar.web.Models.RadarSubjectBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
    private RadarInstanceServiceFactory radarInstanceServiceFactory;

    @Autowired
    private TechnologyService technologyService;

    @RequestMapping(value ={"/radarsubject/search", "/public/radarsubject/search"})
    public ModelAndView technologySearch(ModelAndView model)
    {
        model.setViewName("radarsubject/search");

        if(this.getCurrentUser()!=null)
        {
            model.addObject("userId", this.getCurrentUser().getId());
        }
        return model;
    }

    @RequestMapping(value={"/radarsubject/{id}", "/public/radarsubject/{id}"})
    public ModelAndView getTechnologyDetails(@PathVariable Long id, ModelAndView model)
    {
        Technology targetTechnology = this.technologyService.findById(id);
        RadarSubjectBreakdown viewModel = new RadarSubjectBreakdown(targetTechnology);
        List<Radar> ownedRadarList = new ArrayList<Radar>();

        if(this.getCurrentUser()!=null)
        {
            ownedRadarList = radarInstanceServiceFactory.getFullHistory().getAllOwnedByTechnologyId(id, this.getCurrentUser());
        }

        for(int i = 0; i < ownedRadarList.size(); i++)
        {
            viewModel.addOwnedRadarSubjectAssessment(ownedRadarList.get(i));
        }

        List<Radar> publishedRadarList = radarInstanceServiceFactory.getMostRecent().getAllNotOwnedByTechnologyId(id, this.getCurrentUser());

        for(int i = 0; i < publishedRadarList.size(); i++)
        {
            viewModel.addPublishedRadarSubjectAssessment(publishedRadarList.get(i));
        }

        model.setViewName("radarsubject/details");
        model.addObject("targetTechnology", targetTechnology);
        model.addObject("assessmentItems", viewModel);

        if(this.getCurrentUser()!=null)
        {
            model.addObject("userId", this.getCurrentUser().getId());
        }

        return model;
    }
}
