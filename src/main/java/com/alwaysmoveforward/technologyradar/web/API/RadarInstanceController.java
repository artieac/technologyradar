package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 12/24/2017.
 */
@Controller
@RequestMapping("/api")
public class RadarInstanceController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarService radarService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @RequestMapping(value = {"/User/{radarUserId}/Radars", "/public/User/{radarUserId}/Radars"}, method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Radar> getRadarsByUser(@PathVariable Long radarUserId)
    {
        return this.radarService.findByRadarUserId(radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar", method = RequestMethod.POST)
    public @ResponseBody List<Radar> addRadar(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        if(this.getCurrentUser().getId() == radarUserId)
        {
            this.radarService.addRadar(radarUserId, modelMap.get("name").toString());
        }

        return this.radarService.findByRadarUserId(this.getCurrentUser().getId());
    }

    @RequestMapping(value = {"/User/{radarUserId}/Radar/{radarId}", "/public/User/{radarUserId}/Radar/{radarId}"}, produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        return this.radarSetupService.generateDiagramData(radarUserId, radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.PUT)
    public @ResponseBody List<Radar> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        if(this.getCurrentUser().getId() == radarUserId)
        {
            this.radarService.updateRadar(radarUserId, radarId, modelMap.get("name").toString());
        }

        return this.radarService.findByRadarUserId(this.getCurrentUser().getId());
    }
}
