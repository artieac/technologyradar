package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
@Controller
@RequestMapping("/api")
public class RadarConfigurationController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarConfigurationController.class);

    @Autowired
    DiagramConfigurationService radarSetupService;

    @Autowired
    RadarService radarService;

    @RequestMapping(value = "/radar/rings", produces = "application/json")
    public @ResponseBody List<RadarRing> getRadarRings()
    {
        List<RadarRing> retVal = this.radarSetupService.getRadarRings();

        return retVal;
    }

    @RequestMapping(value = "/radar/categories", produces = "application/json")
    public @ResponseBody List<RadarCategory> getRadarCategories()
    {
        List<RadarCategory> retVal = this.radarSetupService.getRadarCategories();

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteUserRadar(@PathVariable Long radarId, @PathVariable Long radarUserId)
    {
        return this.radarService.deleteRadar(radarUserId, radarId);
    }
}
