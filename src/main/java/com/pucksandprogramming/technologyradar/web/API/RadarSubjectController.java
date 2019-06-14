package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.HomeController;
import com.pucksandprogramming.technologyradar.web.Models.RadarSubjectBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller("RadarSubjectAPI")
@RequestMapping("/api/public/RadarSubject")
public class RadarSubjectController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private RadarInstanceService radarInstanceService;

    @Autowired
    private TechnologyService technologyService;

    @RequestMapping("/{id}/assessments")
    public @ResponseBody
    RadarSubjectBreakdown getRadarSubjects(@PathVariable Long id)
    {
        Technology targetTechnology = this.radarInstanceService.findTechnologyById(id);
        RadarSubjectBreakdown retVal = new RadarSubjectBreakdown(targetTechnology);

        if(targetTechnology != null)
        {
            // TBD this gets all the assessment items at the moment, ideally it would just pull back the ones targeted
            // that would make the subsequent calls at lot easier to manage.
            List<Radar> foundItems = radarInstanceService.getAllByTechnologyId(id);

            for(int i = 0; i < foundItems.size(); i++)
            {
                retVal.addRadarSubjectAssessment(foundItems.get(i), this.getCurrentUser());
            }
        }

        return retVal;
    }

    @RequestMapping("/search")
    public @ResponseBody List<Technology> searchRadarSubjects(@RequestParam Map<String, String> allRequestParams)
    {
        String radarSubjectName = "";

        if(allRequestParams.containsKey("name"))
        {
            radarSubjectName = allRequestParams.get("name");
        }

        Long radarRingId = new Long(-1);
        if(allRequestParams.containsKey("radarRingId"))
        {
            radarRingId = Long.parseLong(allRequestParams.get("radarRingId"));
        }

        Long radarCategoryId = new Long(-1);
        if(allRequestParams.containsKey("radarCategoryId"))
        {
            radarCategoryId = Long.parseLong(allRequestParams.get("radarCategoryId"));
        }

        List<Technology> retVal = this.technologyService.searchTechnology(radarSubjectName, radarRingId, radarCategoryId);

        return retVal;
    }
}
