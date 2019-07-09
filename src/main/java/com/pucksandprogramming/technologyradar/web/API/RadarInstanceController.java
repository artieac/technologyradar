package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.web.Models.PublishRadarModel;
import com.pucksandprogramming.technologyradar.web.Models.UserViewModel;
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
public class RadarInstanceController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    RadarUserService userService;

    @Autowired
    private RadarServiceFactory radarServiceFactory;

    @Autowired
    private DiagramConfigurationService radarSetupService;


    @GetMapping(value = "/User/{radarUserId}/Radars", produces = "application/json")
    public @ResponseBody
    List<Radar> getAllRadarsByUser(@PathVariable Long radarUserId,
                                   @RequestParam(name = "radarTypeId", required = false, defaultValue = "") String radarTypeId,
                                   @RequestParam(name = "radarTypeVersion", required = false, defaultValue = "-1") Long radarTypeVersion) {
        List<Radar> retVal = new ArrayList<Radar>();

        try
        {
            if (this.getCurrentUser().getId() == radarUserId)
            {
                RadarUser targetUser = this.userService.findOne(radarUserId);
                radarServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

                if (radarTypeId == null || radarTypeId.isEmpty())
                {
                    retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(radarUserId, false);
                }
                else
                {
                    if (radarTypeVersion < 0)
                    {
                        retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().findByUserAndType(radarUserId, radarTypeId, false);
                    }
                    else
                    {
                        retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().findByUserTypeAndVersion(radarUserId, radarTypeId, radarTypeVersion, false);
                    }
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }


        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar")
    public @ResponseBody
    List<Radar> addRadar(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();

        try
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);
            radarServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

            if (this.getCurrentUser().getId() == radarUserId) {
                String radarName = modelMap.get("name").toString();
                String radarTypeId = modelMap.get("radarTypeId").toString();
                Long radarTypeVersion = Long.parseLong(modelMap.get("radarTypeVersion").toString());
                this.radarServiceFactory.getRadarTypeServiceForSharing().addRadar(radarUserId, radarName, radarTypeId, radarTypeVersion);
            }

            retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(this.getCurrentUser().getId(), false);
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }


    @GetMapping(value = "/User/{radarUserId}/Radar/{radarId}", produces = "application/json")
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = new DiagramPresentation();

        try
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);
            Radar targetRadar = this.radarServiceFactory.getRadarTypeServiceForSharing().findByUserAndRadarId(this.getCurrentUser().getId(), radarId, false);
            retVal = this.radarSetupService.generateDiagramData(radarUserId, targetRadar);
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/Radar/{radarId}")
    public @ResponseBody List<Radar> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        List<Radar> retVal = new ArrayList<>();

        try
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);
            radarServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);

            if(this.getCurrentUser().getId() == radarUserId)
            {
                this.radarServiceFactory.getFullHistory().updateRadar(radarUserId, radarId, modelMap.get("name").toString());
            }

            retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().findByRadarUserId(this.getCurrentUser().getId(), false);
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/Radar/{radarId}/Publish")
    public @ResponseBody
    PublishRadarModel updateRadarIsPublished(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId)
    {
        PublishRadarModel retVal = new PublishRadarModel();

        try
        {
            boolean isPublished = Boolean.parseBoolean(modelMap.get("isPublished").toString());

            if(this.getCurrentUser().getId() == userId)
            {
                radarServiceFactory.setUserDetails(this.getCurrentUser(), this.getCurrentUser());
                retVal.setPublishSucceeded(this.radarServiceFactory.getRadarTypeServiceForSharing().publishRadar(userId, radarId, isPublished));
                retVal.setRadars(this.radarServiceFactory.getFullHistory().findByRadarUserId(userId, false));
                UserViewModel currentUser = new UserViewModel(this.userService.findOne(userId));
                currentUser.setNumberOfSharedRadar(this.radarServiceFactory.getRadarTypeServiceForSharing().getSharedRadarCount(userId));
                retVal.setCurrentUser(currentUser);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/Radar/{radarId}/Lock")
    public @ResponseBody boolean updateRadarIsLocked(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId)
    {
        boolean retVal = false;

        try
        {
            boolean isLocked = Boolean.parseBoolean(modelMap.get("isLocked").toString());

            if (this.getCurrentUser().getId() == userId)
            {
                RadarUser targetUser = this.userService.findOne(userId);
                radarServiceFactory.setUserDetails(this.getCurrentUser(), targetUser);
                retVal = this.radarServiceFactory.getRadarTypeServiceForSharing().lockRadar(userId, radarId, isLocked);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @DeleteMapping(value = "/User/{radarUserId}/Radar/{radarId}")
    public @ResponseBody List<Radar> deleteUserRadar(@PathVariable Long radarId, @PathVariable Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();

        try
        {
            if (this.radarServiceFactory.getFullHistory().deleteRadar(radarUserId, radarId))
            {
                retVal = this.radarServiceFactory.getFullHistory().findByRadarUserId(radarUserId, false);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }
}
