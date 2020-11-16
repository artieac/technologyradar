package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarAccessManager;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.web.Models.PublishRadarModel;
import com.pucksandprogramming.technologyradar.web.Models.RadarViewModel;
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
public class RadarController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    RadarUserService userService;

    @Autowired
    private RadarService radarService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @Autowired
    private RadarAccessManager radarAccessManager;

    @GetMapping(value = "/public/User/{radarUserId}/Radar/mostRecent", produces = "application/json")
    public @ResponseBody
    RadarViewModel getPublicMostRecentRadarByUser(@PathVariable Long radarUserId) {
        RadarViewModel retVal = null;

        List<Radar> foundRadar = radarService.findByRadarUserId(radarUserId);

        if(foundRadar!=null && foundRadar.size() > 0) {
            retVal = new RadarViewModel(foundRadar.get(0));
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{radarUserId}/Radars", "/public/User/{radarUserId}/Radars"}, produces = "application/json")
    public @ResponseBody
    List<RadarViewModel> getAllRadarsByUser(@PathVariable Long radarUserId,
                                            @RequestParam(name = "radarTemplateId", required = false, defaultValue = "-1") Long radarTemplateId) {
        List<RadarViewModel> retVal = new ArrayList<RadarViewModel>();

        try {
            List<Radar> foundItems = new ArrayList<>();

            RadarUser targetUser = this.userService.findOne(radarUserId);

            if (radarTemplateId > 0) {
                foundItems = this.radarService.findByUserAndType(radarUserId, radarTemplateId);
            }
            else {
                foundItems = this.radarService.findByRadarUserId(radarUserId);
            }

            if(foundItems != null) {
                for(Radar foundItem : foundItems) {
                    retVal.add(new RadarViewModel(foundItem));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }


        return retVal;
    }

    @GetMapping(value = {"/User/{radarUserId}/RadarTemplate/{RadarTemplate}/Radar/FullView", "/public/User/{radarUserId}/RadarTemplate/{radarTemplateId}/Radar/FullView"}, produces = "application/json")
    public @ResponseBody
    DiagramPresentation getMostRecentRadar(@PathVariable Long radarUserId,
                           @PathVariable Long radarTemplateId) {
        DiagramPresentation retVal = new DiagramPresentation();

        try {
            RadarUser targetUser = this.userService.findOne(radarUserId);
            Radar targetRadar = this.radarService.findCurrentByType(radarUserId, radarTemplateId);
            retVal = this.radarSetupService.generateDiagramData(targetUser.getId(), targetRadar);
        }
        catch(Exception e) {
            logger.error(e);
        }


        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar")
    public @ResponseBody
    List<RadarViewModel> addRadar(@RequestBody Map modelMap, @PathVariable Long radarUserId) {
        List<RadarViewModel> retVal = new ArrayList<>();

        try {
            RadarUser targetUser = this.userService.findOne(radarUserId);

            if (this.getCurrentUser().getId() == radarUserId) {
                String radarName = modelMap.get("name").toString();
                Long radarTemplateId = Long.parseLong(modelMap.get("radarTemplateId").toString());
                this.radarService.addRadar(radarUserId, radarName, radarTemplateId);
            }

            List<Radar> foundItems = this.radarService.findByRadarUserId(targetUser.getId());

            if(foundItems != null) {
                for(Radar foundItem : foundItems) {
                    retVal.add(new RadarViewModel(foundItem));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }


    @GetMapping(value = {"/User/{radarUserId}/Radar/{radarId}", "/public/User/{radarUserId}/Radar/{radarId}"}, produces = "application/json")
    public @ResponseBody DiagramPresentation getRadarInstance(@PathVariable Long radarUserId, @PathVariable Long radarId) {
        DiagramPresentation retVal = new DiagramPresentation();

        try {
            RadarUser targetUser = this.userService.findOne(radarUserId);

            if(targetUser!=null) {
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

    @GetMapping(value ="/User/Radar/{radarId}/CanEdit", produces = "application/json")
    public @ResponseBody boolean canEditRadar(@PathVariable Long radarId) {
        boolean retVal = false;

        try {
            if (this.getCurrentUser() != null) {
                Radar targetRadar = this.radarService.findById(radarId);

                if (targetRadar != null) {
                    if(this.radarAccessManager.canModifyRadar(targetRadar.getRadarUser())) {
                        if (this.getCurrentUser().getId() == targetRadar.getRadarUser().getId()) {
                            if (targetRadar.getIsLocked() == false) {
                                retVal = true;
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/Radar/{radarId}")
    public @ResponseBody List<RadarViewModel> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId) {
        List<RadarViewModel> retVal = new ArrayList<>();

        try {
            RadarUser targetUser = this.userService.findOne(radarUserId);

            if(this.getCurrentUser().getId() == radarUserId) {
                this.radarService.updateRadar(radarUserId, radarId, modelMap.get("name").toString());
            }

            List<Radar> foundItems = this.radarService.findByRadarUserId(this.getCurrentUser().getId());

            if(foundItems != null) {
                for(Radar foundItem : foundItems) {
                    retVal.add(new RadarViewModel(foundItem));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/Radar/{radarId}/Publish")
    public @ResponseBody
    PublishRadarModel updateRadarIsPublished(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId) {
        PublishRadarModel retVal = new PublishRadarModel();

        try {
            boolean isPublished = Boolean.parseBoolean(modelMap.get("isPublished").toString());

            RadarUser targetUser = this.userService.findOne(userId);

            retVal.setPublishSucceeded(this.radarService.publishRadar(userId, radarId, isPublished));
            retVal.setRadars(this.radarService.findByRadarUserId(userId));

            UserViewModel currentUser = new UserViewModel(this.userService.findOne(userId));
            currentUser.setNumberOfSharedRadar(this.radarService.getSharedRadarCount(userId));
            retVal.setCurrentUser(currentUser);
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/Radar/{radarId}/Lock")
    public @ResponseBody boolean updateRadarIsLocked(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarId) {
        boolean retVal = false;

        try {
            boolean isLocked = Boolean.parseBoolean(modelMap.get("isLocked").toString());

            RadarUser targetDataOwner = this.userService.findOne(userId);
            retVal = this.radarService.lockRadar(userId, radarId, isLocked);
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @DeleteMapping(value = "/User/{radarUserId}/Radar/{radarId}")
    public @ResponseBody List<RadarViewModel> deleteUserRadar(@PathVariable Long radarId, @PathVariable Long radarUserId) {
        List<RadarViewModel> retVal = new ArrayList<>();

        try {
            RadarUser targetDataOwner = this.userService.findOne(radarUserId);

            if(targetDataOwner != null) {
                List<Radar> foundItems = null;

                if (this.radarService.deleteRadar(radarUserId, radarId)) {
                    foundItems = this.radarService.findByRadarUserId(radarUserId);
                }

                if (foundItems != null) {
                    for (Radar foundItem : foundItems) {
                        retVal.add(new RadarViewModel(foundItem));
                    }
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }
}
