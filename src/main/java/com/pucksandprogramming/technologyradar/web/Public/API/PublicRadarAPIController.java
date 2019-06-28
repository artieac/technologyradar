package com.pucksandprogramming.technologyradar.web.Public.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.API.RadarInstanceController;
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
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarTypeServiceFactory radarTypeServiceFactory;

    @Autowired
    private RadarInstanceServiceFactory radarInstanceServiceFactory;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @Autowired
    private RadarUserService userService;

    @RequestMapping(value = "/public/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarType> getPublicRadarTypes()
    {
        this.radarTypeServiceFactory.setUser(this.getCurrentUser(), null);
        return radarTypeServiceFactory.getRadarTypeServiceForViewing().findTypesByPublishedRadars(this.getCurrentUser());
    }

    @RequestMapping(value = "/public/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<RadarType> getPublicRadarTypes(@PathVariable Long radarUserId)
    {
        RadarUser targetUser = this.userService.findOne(radarUserId);
        this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
        return radarTypeServiceFactory.getRadarTypeServiceForSharing().findTypesByPublishedRadars(targetUser);
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radars", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Radar> getPublicRadarsByUser(@PathVariable Long radarUserId,
                                      @RequestParam(name = "radarTypeId", required = false, defaultValue = "-1") Long radarTypeId,
                                      @RequestParam(name = "radarTypeVersion", required = false, defaultValue = "-1") Long radarTypeVersion) {
        List<Radar> retVal = new ArrayList<>();

        RadarUser targetUser = this.userService.findOne(radarUserId);
        radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

        if (radarTypeId < 0)
        {
            retVal = radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(radarUserId, true);
        }
        else
        {
            if(radarTypeVersion < 0)
            {
                retVal = radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserAndType(radarUserId, radarTypeId, true);
            }
            else
            {
                retVal = radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserTypeAndVersion(radarUserId, radarTypeId, radarTypeVersion, true);
            }
        }

        return retVal;
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Radar getPublicMostRecentRadarByUser(@PathVariable Long radarUserId)
    {
        Radar retVal = null;

        List<Radar> foundRadar = this.radarInstanceServiceFactory.getMostRecent().findByRadarUserId(radarUserId, true);

        if(foundRadar!=null && foundRadar.size() > 0)
        {
            retVal = foundRadar.get(0);
        }

        return retVal;
    }

    @RequestMapping(value = "/public/User/{radarUserId}/Radar/{radarId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    DiagramPresentation getPublicRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = null;

        RadarUser owningUser = this.userService.findOne(radarUserId);
        radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), owningUser);

        Radar targetRadar = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserAndRadarId(radarUserId, radarId, true);
        retVal = this.radarSetupService.generateDiagramData(radarUserId, targetRadar);

        return retVal;
    }
}