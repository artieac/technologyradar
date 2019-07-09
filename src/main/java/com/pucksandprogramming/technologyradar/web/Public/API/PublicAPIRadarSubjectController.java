package com.pucksandprogramming.technologyradar.web.Public.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstance.PublicRadarService;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarSubjectBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("/api/public/RadarSubject")
public class PublicAPIRadarSubjectController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(PublicAPIRadarSubjectController.class);

    @Autowired
    private PublicRadarService radarInstanceService;

    @Autowired
    private TechnologyService technologyService;

    @GetMapping("/{id}/assessments")
    public @ResponseBody
    RadarSubjectBreakdown getRadarSubjects(@PathVariable Long id)
    {
        Technology targetTechnology = this.technologyService.findById(id);
        RadarSubjectBreakdown retVal = new RadarSubjectBreakdown(targetTechnology);

        if(targetTechnology != null)
        {
            List<Radar> publishedRadarList = radarInstanceService.getAllByTechnologyId(id);

            for(int i = 0; i < publishedRadarList.size(); i++)
            {
                retVal.addPublishedRadarSubjectAssessment(publishedRadarList.get(i));
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

        String radarTypeId = "";
        if(allRequestParams.containsKey("radarTypeId"))
        {
            radarTypeId = allRequestParams.get("radarTypeId").toString();
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

        List<Technology> retVal = this.technologyService.searchTechnology(radarSubjectName, radarTypeId, radarRingId, radarCategoryId);

        return retVal;
    }
}

