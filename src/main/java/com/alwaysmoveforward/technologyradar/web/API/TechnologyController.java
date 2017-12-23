package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.services.TechnologyService;
import com.alwaysmoveforward.technologyradar.web.HomeController;
import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/api/technology")
public class TechnologyController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private TechnologyAssessmentService technologyAssessmentService;

    @Autowired
    private TechnologyService technologyService;

    @RequestMapping("/{id}/assessments")
    public @ResponseBody TechnologyBreakdown getTechnologyAssessments(@PathVariable Long id)
    {
        Technology targetTechnology = this.technologyAssessmentService.findTechnologyById(id);
        TechnologyBreakdown retVal = new TechnologyBreakdown(targetTechnology);

        if(targetTechnology != null)
        {
            // TBD this gets all the assessment items at the moment, ideally it would just pull back the ones targeted
            // that would make the subsequent calls at lot easier to manage.
            List<TechnologyAssessment> foundItems = technologyAssessmentService.getTechnologyAssessmentsByTechnologyId(id);

            for(int i = 0; i < foundItems.size(); i++)
            {
                retVal.addTechnologyAssessment(foundItems.get(i));
            }
        }

        return retVal;
    }

    @RequestMapping("/search")
    public @ResponseBody List<Technology> searchTechnology(@RequestParam Map<String, String> allRequestParams)
    {
        String technologyName = "";

        if(allRequestParams.containsKey("technologyName")){
            technologyName = allRequestParams.get("technologyName");
        }

        Long radarRingId = new Long(-1);
        if(allRequestParams.containsKey("radarRingId")){
            radarRingId = Long.parseLong(allRequestParams.get("radarRingId"));
        }

        Long radarCategoryId = new Long(-1);
        if(allRequestParams.containsKey("radarCategoryId")){
            radarCategoryId = Long.parseLong(allRequestParams.get("radarCategoryId"));
        }

        List<Technology> retVal = this.technologyService.searchTechnology(technologyName, radarRingId, radarCategoryId);

        return retVal;
    }
}
