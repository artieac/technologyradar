package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
@Controller
@RequestMapping("/api/radar")
public class RadarController
{
    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    DiagramConfigurationService radarSetupService;

    @Autowired
    TechnologyAssessmentService technolgyAssessmentService;

    @RequestMapping(value = "/rings", produces = "application/json")
    public @ResponseBody List<RadarRing> getRadarRings()
    {
        List<RadarRing> retVal = this.radarSetupService.getRadarRings();

        return retVal;
    }

    @RequestMapping(value = "/categories", produces = "application/json")
    public @ResponseBody List<RadarCategory> getRadarCategories()
    {
        List<RadarCategory> retVal = this.radarSetupService.getRadarCategories();

        return retVal;
    }
}
