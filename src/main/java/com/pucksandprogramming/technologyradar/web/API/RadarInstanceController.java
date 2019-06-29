package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
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
    RadarUserService userService;

    @Autowired
    private RadarInstanceServiceFactory radarInstanceServiceFactory;

    @Autowired
    private DiagramConfigurationService radarSetupService;


    @RequestMapping(value = "/User/{radarUserId}/Radars", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Radar> getAllRadarsByUser(@PathVariable Long radarUserId,
                                                        @RequestParam(name="radarTypeId", required = false, defaultValue="") String radarTypeId,
                                                        @RequestParam(name="radarTypeVersion", required=false, defaultValue="-1") Long radarTypeVersion)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        if(this.getCurrentUser().getId()==radarUserId)
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);
            radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

            if (radarTypeId == null || radarTypeId == "")
            {
                retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(radarUserId, false);
            }
            else
            {
                if (radarTypeVersion < 0)
                {
                    retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserAndType(radarUserId, radarTypeId, false);
                }
                else
                {
                    retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserTypeAndVersion(radarUserId, radarTypeId, radarTypeVersion, false);
                }
            }
        }
        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar", method = RequestMethod.POST)
    public @ResponseBody List<Radar> addRadar(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        RadarUser targetUser = this.userService.findOne(radarUserId);
        radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

        if(this.getCurrentUser().getId() == radarUserId)
        {
            String radarName = modelMap.get("name").toString();
            String radarTypeId = modelMap.get("radarTypeId").toString();
            Long radarTypeVersion = Long.parseLong(modelMap.get("radarTypeVersion").toString());
            this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().addRadar(radarUserId, radarName, radarTypeId, radarTypeVersion);
        }

        return this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(this.getCurrentUser().getId(), false);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        RadarUser targetUser = this.userService.findOne(radarUserId);
        Radar retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserAndRadarId(this.getCurrentUser().getId(), radarId, false);
        return this.radarSetupService.generateDiagramData(radarUserId, retVal);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.PUT)
    public @ResponseBody List<Radar> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        RadarUser targetUser = this.userService.findOne(radarUserId);
        radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

        if(this.getCurrentUser().getId() == radarUserId)
        {
            this.radarInstanceServiceFactory.getFullHistory().updateRadar(radarUserId, radarId, modelMap.get("name").toString());
        }

        return this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(this.getCurrentUser().getId(), false);
    }

    @RequestMapping(value = "/User/{userId}/Radar/{radarId}/Publish", method = RequestMethod.PUT)
    public @ResponseBody boolean updateRadarIsPublished(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId)
    {
        boolean retVal = false;
        boolean isPublished = Boolean.parseBoolean(modelMap.get("isPublished").toString());

        if(this.getCurrentUser().getId() == userId)
        {
            RadarUser targetUser = this.userService.findOne(userId);
            radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

            retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().publishRadar(userId, radarId, isPublished);
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
            RadarUser targetUser = this.userService.findOne(userId);
            radarInstanceServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);
            retVal = this.radarInstanceServiceFactory.getRadarTypeServiceForSharing().lockRadar(userId, radarId, isLocked);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.DELETE)
    public @ResponseBody List<Radar> deleteUserRadar(@PathVariable Long radarId, @PathVariable Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();

        if(this.radarInstanceServiceFactory.getFullHistory().deleteRadar(radarUserId, radarId))
        {
            retVal = this.radarInstanceServiceFactory.getFullHistory().findByRadarUserId(radarUserId, false);
        }

        return retVal;
    }
}
