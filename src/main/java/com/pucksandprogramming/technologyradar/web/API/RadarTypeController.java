package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.*;
import com.pucksandprogramming.technologyradar.services.RadarType.AssociatedRadarTypeService;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeServiceFactory;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarTypeViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class RadarTypeController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    RadarTypeServiceFactory radarTypeServiceFactory;

    @Autowired
    AssociatedRadarTypeService associatedRadarTypeService;

    @GetMapping(value = "/User/{radarUserId}/RadarTypes", produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@PathVariable Long radarUserId,
                                                                        @RequestParam(name="allVersions", required = false, defaultValue = "false") boolean allVersions,
                                                                        @RequestParam(name="includeOwned", required = false, defaultValue = "true") boolean includeOwned,
                                                                        @RequestParam(name="includeAssociated", required = false, defaultValue = "false") boolean includeAssociated)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            RadarUser targetUser = radarUserService.findOne(radarUserId);
            List<RadarType> foundItems = new ArrayList<RadarType>();

            if (targetUser != null)
            {
                if (includeOwned == true)
                {
                    if (allVersions == true)
                    {
                        foundItems = radarTypeServiceFactory.getRadarTypeService(targetUser).findAllByUserId(radarUserId);
                    }
                    else
                    {
                        foundItems = radarTypeServiceFactory.getMostRecent().findAllByUserId(radarUserId);
                    }
                }

                if (includeAssociated == true)
                {
                    List<RadarType> associatedRadarTypes = this.associatedRadarTypeService.findAssociatedRadarTypes(targetUser);
                    foundItems.addAll(associatedRadarTypes);
                }
            }

            for (RadarType radarTypeItem : foundItems)
            {
                retVal.add(new RadarTypeViewModel(radarTypeItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/RadarTypes/Shared", produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@RequestParam(name="ownedBy", required = false, defaultValue = "-1") Long ownerUserId,
                                                                        @RequestParam(name="excludeUser", required = false, defaultValue = "-1") Long excludeUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            List<RadarType> foundItems = new ArrayList<RadarType>();

            if (excludeUserId != null && excludeUserId > 0)
            {
                RadarUser targetUser = radarUserService.findOne(excludeUserId);

                if (targetUser != null)
                {
                    foundItems = this.radarTypeServiceFactory.getRadarTypeService(targetUser).findSharedRadarTypes(targetUser.getId());
                }
            }

            for (RadarType radarTypeItem : foundItems)
            {
                retVal.add(new RadarTypeViewModel(radarTypeItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/User/{radarUserId}/RadarTypes/Associated", produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getAssociatedRadarTypes(@PathVariable Long radarUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            List<RadarType> foundItems = new ArrayList<RadarType>();

            if (radarUserId != null && radarUserId > 0)
            {
                RadarUser targetUser = radarUserService.findOne(radarUserId);

                if (targetUser != null)
                {
                    foundItems = this.associatedRadarTypeService.findAssociatedRadarTypes(targetUser);
                }
            }

            for (RadarType radarTypeItem : foundItems)
            {
                retVal.add(new RadarTypeViewModel(radarTypeItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@PathVariable Long radarUserId,
                                                                        @PathVariable String radarTypeId,
                                                                        @RequestParam(name="allVersions", required = false, defaultValue = "false") boolean allVersions) {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            RadarUser targetUser = radarUserService.findOne(radarUserId);
            List<RadarType> foundItems = new ArrayList<RadarType>();

            if (targetUser != null)
            {
                if (allVersions == true)
                {
                    foundItems = radarTypeServiceFactory.getRadarTypeService(targetUser).findAllByUserAndRadarType(radarUserId, radarTypeId);
                }
                else
                {
                    foundItems = radarTypeServiceFactory.getMostRecent().findAllByUserAndRadarType(radarUserId, radarTypeId);
                }
            }

            for (RadarType radarTypeItem : foundItems)
            {
                retVal.add(new RadarTypeViewModel(radarTypeItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/RadarType", produces = "application/json")
    public @ResponseBody
    RadarTypeViewModel addRadarType(@RequestBody RadarTypeViewModel radarTypeViewModel, @PathVariable Long radarUserId)
    {
        RadarTypeViewModel retVal = null;

        try
        {
            if (radarTypeViewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);
                RadarType radarType = radarTypeViewModel.ConvertToRadarType();
                radarType = this.radarTypeServiceFactory.getRadarTypeService(targetUser).update(radarType, radarUserId);
                retVal = new RadarTypeViewModel(radarType);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", produces = "application/json")
    public @ResponseBody
    RadarTypeViewModel updateRadarType(@RequestBody RadarTypeViewModel radarTypeViewModel, @PathVariable Long radarUserId, @PathVariable Long radarTypeId)
    {
        RadarTypeViewModel retVal = null;

        try
        {
            if (radarTypeViewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);
                RadarType radarType = radarTypeViewModel.ConvertToRadarType();
                radarType = this.radarTypeServiceFactory.getRadarTypeService(targetUser).update(radarType, radarUserId);
                retVal = new RadarTypeViewModel(radarType);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/RadarType/{radarTypeId}/Version/{radarTypeVersion}/Associate")
    public @ResponseBody boolean associateRadarType(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable String radarTypeId, @PathVariable Long radarTypeVersion)
    {
        boolean retVal = false;

        try
        {
            boolean shouldAssociate = Boolean.parseBoolean(modelMap.get("shouldAssociate").toString());

            if (this.getCurrentUser().getId() == userId)
            {
                retVal = this.associatedRadarTypeService.associateRadarType(this.getCurrentUser(), radarTypeId, radarTypeVersion, shouldAssociate);
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }
}
