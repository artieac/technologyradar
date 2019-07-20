package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
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
    private RadarService radarService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @GetMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", produces = "application/json")
    public @ResponseBody Radar getPublicMostRecentRadarByUser(@PathVariable Long radarUserId)
    {
        Radar retVal = null;

        List<Radar> foundRadar = radarService.findByRadarUserId(radarUserId);

        if(foundRadar!=null && foundRadar.size() > 0)
        {
            retVal = foundRadar.get(0);
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{radarUserId}/Radars", "/public/User/{radarUserId}/Radars"}, produces = "application/json")
    public @ResponseBody
    List<Radar> getAllRadarsByUser(@PathVariable Long radarUserId,
                                   @RequestParam(name = "radarTypeId", required = false, defaultValue = "") String radarTypeId,
                                   @RequestParam(name = "radarTypeVersion", required = false, defaultValue = "-1") Long radarTypeVersion) {
        List<Radar> retVal = new ArrayList<Radar>();

        try
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);

            if (radarTypeId == null || radarTypeId.isEmpty())
            {
                retVal = this.radarService.findByRadarUserId(radarUserId);
            }
            else
            {
                if (radarTypeVersion < 0)
                {
                    retVal = this.radarService.findByUserAndType(radarUserId, radarTypeId);
                }
                else
                {
                    retVal = this.radarService.findByUserTypeAndVersion(radarUserId, radarTypeId, radarTypeVersion);
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

            if (this.getCurrentUser().getId() == radarUserId) {
                String radarName = modelMap.get("name").toString();
                String radarTypeId = modelMap.get("radarTypeId").toString();
                Long radarTypeVersion = Long.parseLong(modelMap.get("radarTypeVersion").toString());
                this.radarService.addRadar(radarUserId, radarName, radarTypeId, radarTypeVersion);
            }

            retVal = this.radarService.findByRadarUserId(targetUser.getId());
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }


    @GetMapping(value = {"/User/{radarUserId}/Radar/{radarId}", "/public/User/{radarUserId}/Radar/{radarId}"}, produces = "application/json")
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = new DiagramPresentation();

        try
        {
            RadarUser targetUser = this.userService.findOne(radarUserId);

            if(targetUser!=null)
            {
                Radar targetRadar = this.radarService.findByUserAndRadarId(targetUser.getId(), radarId);
                retVal = this.radarSetupService.generateDiagramData(targetUser.getId(), targetRadar);
            }
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

            if(this.getCurrentUser().getId() == radarUserId)
            {
                this.radarService.updateRadar(radarUserId, radarId, modelMap.get("name").toString());
            }

            retVal = this.radarService.findByRadarUserId(this.getCurrentUser().getId());
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

            RadarUser targetUser = this.userService.findOne(userId);

            retVal.setPublishSucceeded(this.radarService.publishRadar(userId, radarId, isPublished));
            retVal.setRadars(this.radarService.findByRadarUserId(userId));

            UserViewModel currentUser = new UserViewModel(this.userService.findOne(userId));
            currentUser.setNumberOfSharedRadar(this.radarService.getSharedRadarCount(userId));
            retVal.setCurrentUser(currentUser);
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

            RadarUser targetDataOwner = this.userService.findOne(userId);
            retVal = this.radarService.lockRadar(userId, radarId, isLocked);
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
            RadarUser targetDataOwner = this.userService.findOne(radarUserId);
            if(this.radarService.deleteRadar(radarUserId, radarId))
            {
                retVal = this.radarService.findByRadarUserId(radarUserId);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }
}
