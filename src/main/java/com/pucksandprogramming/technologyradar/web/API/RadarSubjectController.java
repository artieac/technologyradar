package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.TechnologyService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.HomeController;
import com.pucksandprogramming.technologyradar.web.Models.RadarSubjectBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller("APIRadarSubjectController")
@RequestScope
@RequestMapping(value={"/api/RadarSubject", "/api/public/RadarSubject"})
public class RadarSubjectController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(HomeController.class);

    private final RadarService radarService;
    private final TechnologyService technologyService;

    @Autowired
    public RadarSubjectController(RadarService radarService,
                                  TechnologyService technologyService){
        this.radarService = radarService;
        this.technologyService = technologyService;
    }

    @GetMapping("/{id}/assessments")
    public @ResponseBody
    RadarSubjectBreakdown getRadarSubjects(@PathVariable Long id) {
        Optional<Technology> targetTechnology = this.technologyService.findById(id);
        RadarSubjectBreakdown retVal = new RadarSubjectBreakdown(targetTechnology.get());

        try {
            if (targetTechnology.isPresent()) {
                // TBD this gets all the assessment items at the moment, ideally it would just pull back the ones targeted
                // that would make the subsequent calls at lot easier to manage.
                List<Radar> ownedRadarList = new ArrayList<Radar>();
                List<Radar> publishedRadarList = new ArrayList<>();

                if (this.getCurrentUser() != null) {
                    ownedRadarList = this.radarService.getAllOwnedByTechnologyId(this.getCurrentUserId(), id);
                    publishedRadarList = this.radarService.getAllNotOwnedByTechnologyId(this.getCurrentUserId(), id);
                }
                else {
                    publishedRadarList = this.radarService.getAllByRadarSubjectId(id);
                }

                for (int i = 0; i < ownedRadarList.size(); i++) {
                    retVal.addOwnedRadarSubjectAssessment(ownedRadarList.get(i));
                }


                for (int i = 0; i < publishedRadarList.size(); i++) {
                    retVal.addPublishedRadarSubjectAssessment(publishedRadarList.get(i));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping("/search")
    public @ResponseBody List<Technology> searchRadarSubjects(@RequestParam Map<String, String> allRequestParams) {
        List<Technology> retVal = new ArrayList<>();

        try {
            String radarSubjectName = "";

            if (allRequestParams.containsKey("name")) {
                radarSubjectName = allRequestParams.get("name");
            }

            String radarTemplateId = "";
            if (allRequestParams.containsKey("radarTemplateId")) {
                radarTemplateId = allRequestParams.get("radarTemplateId").toString();
            }

            Long radarRingId = new Long(-1);
            if (allRequestParams.containsKey("radarRingId")) {
                radarRingId = Long.parseLong(allRequestParams.get("radarRingId"));
            }

            Long radarCategoryId = new Long(-1);
            if (allRequestParams.containsKey("radarCategoryId")) {
                radarCategoryId = Long.parseLong(allRequestParams.get("radarCategoryId"));
            }

            retVal = this.technologyService.searchTechnology(radarSubjectName, radarTemplateId, radarRingId, radarCategoryId);
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }
}
