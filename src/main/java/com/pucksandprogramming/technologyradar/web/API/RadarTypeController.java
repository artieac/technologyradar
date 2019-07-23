package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.*;
import com.pucksandprogramming.technologyradar.services.RadarType.AssociatedRadarTypeService;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeService;
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
    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    RadarTypeService radarTypeService;

    @Autowired
    AssociatedRadarTypeService associatedRadarTypeService;

    @GetMapping(value = "/public/RadarTypes", produces = "application/json")
    public @ResponseBody List<RadarType> getPublicRadarTypes()
    {
        List<RadarType> retVal = new ArrayList();

        if (this.getCurrentUser() != null)
        {
            retVal = radarTypeService.findByUserId(this.getCurrentUserId());
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{radarUserId}/RadarTypes", "/public/User/{radarUserId}/RadarTypes"}, produces = "application/json")
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
                if(allVersions == true)
                {
                    foundItems = radarTypeService.findByUserId(radarUserId);
                }
                else
                {
                    foundItems = radarTypeService.findMostRecentByUserId(radarUserId);
                }

                if (includeAssociated == true && this.getCurrentUser()!=null && this.getCurrentUser().getId()==targetUser.getId())
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

    @GetMapping(value = {"/RadarTypes/Published", "/public/RadarTypes/Published"}, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesForPublishedRadars()
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            List<RadarType> foundItems = radarTypeService.findByPublishedRadars(this.getCurrentUserId());

            if(foundItems!=null)
            {
                for(RadarType radarType : foundItems)
                {
                    retVal.add(new RadarTypeViewModel(radarType));
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{userId}/RadarTypes/Radared", "/public/User/{userId}/RadarTypes/Radared"}, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesForPublishedRadars(@PathVariable Long userId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            List<RadarType> foundItems = radarTypeService.findOwnedWithRadars(userId);

            if(foundItems!=null)
            {
                for(RadarType radarType : foundItems)
                {
                    retVal.add(new RadarTypeViewModel(radarType));
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = {"/RadarTypes/Shared", "/public//RadarTypes/Shared"}, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getRadarTypesByUserId(@RequestParam(name="excludeUser", required = false, defaultValue = "-1") Long excludeUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        try
        {
            List<RadarType> foundItems = this.radarTypeService.findSharedRadarTypes(excludeUserId);

            for (RadarType radarTypeItem : foundItems)
            {
                retVal.add(new RadarTypeViewModel(radarTypeItem));
            }

            return retVal;
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
            List<RadarType> foundItems = radarTypeService.findByUserAndRadarType(radarUserId, radarTypeId);

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

                if(targetUser != null)
                {
                    RadarType radarType = radarTypeViewModel.ConvertToRadarType();
                    radarType = radarTypeService.update(radarType, radarUserId);
                    retVal = new RadarTypeViewModel(radarType);
                }
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

                if(targetUser != null)
                {
                    RadarType radarType = radarTypeViewModel.ConvertToRadarType();
                    radarType = radarTypeService.update(radarType, radarUserId);
                    retVal = new RadarTypeViewModel(radarType);
                }
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
