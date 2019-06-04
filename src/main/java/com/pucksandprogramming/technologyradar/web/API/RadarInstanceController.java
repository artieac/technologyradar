package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private RadarInstanceService radarInstanceService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @RequestMapping(value = "/public/User/{radarUserId}/Radars", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Radar> getPublicRadarsByUser(@PathVariable Long radarUserId, @RequestParam(required = false) String radarTypeId)
    {
        return this.radarInstanceService.findByRadarUserIdAndIsPublished(radarUserId, true);
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Radar getPublicMostRecentRadarByUser(@PathVariable Long radarUserId)
    {
        return this.radarInstanceService.findMostRecentByUserIdAndPublished(radarUserId, true);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radars", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Radar> getAllRadarsByUser(@PathVariable Long radarUserId, @RequestParam(name="radarTypeId", required = false) String radarTypeId) {
        List<Radar> retVal = new ArrayList<Radar>();

        if (radarTypeId != null) {
            retVal = this.radarInstanceService.findByRadarUserIdAndRadarTypeId(radarUserId, Long.parseLong(radarTypeId));
        } else {
            retVal = this.radarInstanceService.findByRadarUserId(radarUserId);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar", method = RequestMethod.POST)
    public @ResponseBody List<Radar> addRadar(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        if(this.getCurrentUser().getId() == radarUserId)
        {
            String radarName = modelMap.get("name").toString();
            Long radarTypeId = Long.parseLong(modelMap.get("radarTypeId").toString());
            this.radarInstanceService.addRadar(radarUserId, radarName, radarTypeId);
        }

        return this.radarInstanceService.findByRadarUserId(this.getCurrentUser().getId());
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/{radarId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    DiagramPresentation getPublicRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        return this.radarSetupService.generateDiagramData(radarUserId, radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        return this.radarSetupService.generateDiagramData(radarUserId, radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.PUT)
    public @ResponseBody List<Radar> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        if(this.getCurrentUser().getId() == radarUserId)
        {
            this.radarInstanceService.updateRadar(radarUserId, radarId, modelMap.get("name").toString());
        }

        return this.radarInstanceService.findByRadarUserId(this.getCurrentUser().getId());
    }

    @RequestMapping(value = "/User/{userId}/Radar/{radarId}/Publish", method = RequestMethod.PUT)
    public @ResponseBody boolean updateRadarIsPublished(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId)
    {
        boolean retVal = false;
        boolean isPublished = Boolean.parseBoolean(modelMap.get("isPublished").toString());

        if(this.getCurrentUser().getId() == userId)
        {
            retVal = this.radarInstanceService.publishRadar(userId, radarId, isPublished);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{userId}/Radar/{radarId}/Lock", method = RequestMethod.PUT)
    public @ResponseBody boolean updateRadarIsLocked(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId)
    {
        boolean retVal = false;
        boolean isLocked = Boolean.parseBoolean(modelMap.get("isLocked").toString());

        if(this.getCurrentUser().getId() == userId)
        {
            retVal = this.radarInstanceService.lockRadar(userId, radarId, isLocked);
        }

        return retVal;
    }

}
