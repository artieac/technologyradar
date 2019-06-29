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
    private AssociatedRadarTypeService associatedRadarTypeService;

    @RequestMapping(value = "/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@PathVariable Long radarUserId,
                                                                        @RequestParam(name="allVersions", required = false, defaultValue = "false") boolean allVersions,
                                                                        @RequestParam(name="includeOwned", required = false, defaultValue = "true") boolean includeOwned,
                                                                        @RequestParam(name="includeAssociated", required = false, defaultValue = "false") boolean includeAssociated)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);
        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(targetUser!=null)
        {
            this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);

            if(includeOwned==true)
            {
                if (allVersions == true)
                {
                    foundItems = radarTypeServiceFactory.getRadarTypeServiceForViewing().findAllByUserId(this.getCurrentUser(), radarUserId);
                }
                else
                {
                    foundItems = radarTypeServiceFactory.getMostRecent().findAllByUserId(this.getCurrentUser(), radarUserId);
                }
            }

            if(includeAssociated==true)
            {
                List<RadarType> associatedRadarTypes = this.associatedRadarTypeService.findAllAssociatedRadarTypes(radarUserId);
                foundItems.addAll(associatedRadarTypes);
            }
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeViewModel(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/RadarTypes/Shared", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@RequestParam(name="ownedBy", required = false, defaultValue = "-1") Long ownerUserId,
                                                                        @RequestParam(name="excludeUser", required = false, defaultValue = "-1") Long excludeUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(excludeUserId != null && excludeUserId > 0)
        {
            RadarUser targetUser = radarUserService.findOne(excludeUserId);

            if (targetUser != null)
            {
                radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
                foundItems = this.radarTypeServiceFactory.getRadarTypeServiceForSharing().findSharedRadarTypes(this.getCurrentUser(), targetUser.getId());
            }
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeViewModel(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarTypes/Associated", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getAssociatedRadarTypes(@PathVariable Long radarUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(radarUserId != null && radarUserId > 0)
        {
            RadarUser targetUser = radarUserService.findOne(radarUserId);

            if (targetUser != null)
            {
                radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
                foundItems = this.radarTypeServiceFactory.getRadarTypeServiceForSharing().findAssociatedRadarTypes(this.getCurrentUser());
            }
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeViewModel(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@PathVariable Long radarUserId,
                                                                        @PathVariable String radarTypeId,
                                                                        @RequestParam(name="allVersions", required = false, defaultValue = "false") boolean allVersions) {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);
        List<RadarType> foundItems = new ArrayList<RadarType>();

        if (targetUser != null)
        {
            this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);

            if (allVersions == true)
            {
                foundItems = radarTypeServiceFactory.getRadarTypeServiceForViewing().findAllByUserAndRadarType(this.getCurrentUser(), radarUserId, radarTypeId);
            }
            else
            {
                foundItems = radarTypeServiceFactory.getMostRecent().findAllByUserAndRadarType(this.getCurrentUser(), radarUserId, radarTypeId);
            }
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeViewModel(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RadarTypeViewModel addRadarType(@RequestBody RadarTypeViewModel radarTypeViewModel, @PathVariable Long radarUserId)
    {
        RadarTypeViewModel retVal = null;

        if(radarTypeViewModel != null)
        {
            RadarUser targetUser = this.radarUserService.findOne(radarUserId);
            this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
            RadarType radarType = radarTypeViewModel.ConvertToRadarType();
            radarType = this.radarTypeServiceFactory.getRadarTypeServiceForSharing().update(radarType, this.getCurrentUser(), radarUserId);
            retVal = new RadarTypeViewModel(radarType);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody
    RadarTypeViewModel updateRadarType(@RequestBody RadarTypeViewModel radarTypeViewModel, @PathVariable Long radarUserId, @PathVariable Long radarTypeId)
    {
        RadarTypeViewModel retVal = null;

        if(radarTypeViewModel != null)
        {
            RadarUser targetUser = this.radarUserService.findOne(radarUserId);
            this.radarTypeServiceFactory.setUser(this.getCurrentUser(), targetUser);
            RadarType radarType = radarTypeViewModel.ConvertToRadarType();
            radarType = this.radarTypeServiceFactory.getRadarTypeServiceForSharing().update(radarType, this.getCurrentUser(), radarUserId);
            retVal = new RadarTypeViewModel(radarType);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{userId}/RadarType/{radarTypeId}/Associate", method = RequestMethod.PUT)
    public @ResponseBody boolean associatedRadarType(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarTypeId)
    {
        boolean retVal = false;
        boolean shouldAssociate = Boolean.parseBoolean(modelMap.get("shouldAssociate").toString());

        if(this.getCurrentUser().getId() == userId)
        {
//            retVal = this.radarTypeService.associatedRadarType(this.getCurrentUser(), radarTypeId, shouldAssociate);
        }

        return retVal;
    }
}
