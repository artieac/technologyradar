package com.pucksandprogramming.technologyradar.web.Public.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PublicRadarAPIController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(PublicRadarAPIController.class);

    @Autowired
    private RadarTypeServiceFactory radarTypeServiceFactory;

    @Autowired
    private RadarServiceFactory radarServiceFactory;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @Autowired
    private RadarUserService userService;

    @GetMapping(value = "/public/RadarTypes", produces = "application/json")
    public @ResponseBody List<RadarType> getPublicRadarTypes()
    {
        this.radarTypeServiceFactory.setUser(this.getCurrentUser(), null);
        return radarTypeServiceFactory.getRadarTypeServiceForViewing().findTypesByPublishedRadars(this.getCurrentUser());
    }

    @GetMapping(value = "/public/User/{radarUserId}/RadarTypes", produces = "application/json")
    public @ResponseBody
    List<RadarType> getPublicRadarTypes(@PathVariable Long radarUserId)
    {
        RadarUser targetUser = this.userService.findOne(radarUserId);
        this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
        return radarTypeServiceFactory.getRadarTypeServiceForSharing().findTypesByPublishedRadars(targetUser);
    }

    @GetMapping(value = "/public/User/{radarUserId}/Radars", produces = "application/json")
    public @ResponseBody
    List<Radar> getPublicRadarsByUser(@PathVariable Long radarUserId,
                                      @RequestParam(name = "radarTypeId", required = false, defaultValue = "") String radarTypeId,
                                      @RequestParam(name = "radarTypeVersion", required = false, defaultValue = "-1") Long radarTypeVersion) {
        List<Radar> retVal = new ArrayList<>();

        RadarUser targetUser = this.userService.findOne(radarUserId);
        radarServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

        if (radarTypeId == null || radarTypeId == "")
        {
            retVal = radarServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(radarUserId, true);
        }
        else
        {
            if(radarTypeVersion < 0)
            {
                retVal = radarServiceFactory.getRadarTypeServiceForSharing().findByUserAndType(radarUserId, radarTypeId, true);
            }
            else
            {
                retVal = radarServiceFactory.getRadarTypeServiceForSharing().findByUserTypeAndVersion(radarUserId, radarTypeId, radarTypeVersion, true);
            }
        }

        return retVal;
    }

    @GetMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", produces = "application/json")
    public @ResponseBody Radar getPublicMostRecentRadarByUser(@PathVariable Long radarUserId)
    {
        Radar retVal = null;

        List<Radar> foundRadar = this.radarServiceFactory.getMostRecent().findByRadarUserId(radarUserId, true);

        if(foundRadar!=null && foundRadar.size() > 0)
        {
            retVal = foundRadar.get(0);
        }

        return retVal;
    }

    @GetMapping(value = "/public/User/{radarUserId}/Radar/{radarId}", produces = "application/json")
    public @ResponseBody
    DiagramPresentation getPublicRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = null;

        RadarUser owningUser = this.userService.findOne(radarUserId);
        radarServiceFactory.setUserDetails(this.getCurrentUser(), owningUser);

        Radar targetRadar = this.radarServiceFactory.getRadarTypeServiceForSharing().findByUserAndRadarId(radarUserId, radarId, true);
        retVal = this.radarSetupService.generateDiagramData(radarUserId, targetRadar);

        return retVal;
    }
}