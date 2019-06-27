package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PublicRadarController {
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);


    @Autowired
    private RadarInstanceService radarInstanceService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @Autowired
    private RadarUserService userService;

    @RequestMapping(value = "/public/User/{radarUserId}/Radars", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Radar> getPublicRadarsByUser(@PathVariable Long radarUserId,
                                      @RequestParam(name = "radarTypeId", required = false, defaultValue = "-1") Long radarTypeId,
                                      @RequestParam(name = "radarTypeVersion", required = false, defaultValue = "-1") Long radarTypeVersion) {
        List<Radar> retVal = new ArrayList<>();

        if (radarTypeId < 0) {
        } else {
        }

        return retVal;
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Radar getPublicMostRecentRadarByUser(@PathVariable Long radarUserId) {
        return this.radarInstanceService.findMostRecentByUserIdAndPublished(radarUserId, true);
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/{radarId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    DiagramPresentation getPublicRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = null;

        RadarUser owningUser = this.userService.findOne(radarUserId);

        if (owningUser.canShareHistory())
        {
            retVal = this.radarSetupService.generateDiagramData(radarUserId, radarId);
        }
        else
        {
            retVal = this.radarSetupService.generateDiagramData(radarUserId, radarId);
        }

        return retVal;
    }
}