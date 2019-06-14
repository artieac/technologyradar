package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import com.pucksandprogramming.technologyradar.services.RadarTypeService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarTypeMessage;
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
    private RadarTypeService radarTypeService;

    @Autowired
    private RadarInstanceService radarInstanceService;


    @RequestMapping(value = "/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> getRadarTypes()
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        List<RadarType> foundItems = this.radarTypeService.findAll(true);

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeMessage(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> getRadarTypesByUserId(@PathVariable Long radarUserId,
                                                                      @RequestParam(name="includeOwned", required = false, defaultValue = "true") boolean includeOwned,
                                                                      @RequestParam(name="includeSharedByOthers", required = false, defaultValue = "false") boolean includeSharedByOthers,
                                                                      @RequestParam(name="includeAssociated", required = false, defaultValue = "false") boolean includeAssociated)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);
        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(targetUser!=null)
        {
            if (this.getCurrentUser() != null && targetUser.getId() == this.getCurrentUser().getId())
            {
                if (includeOwned == true)
                {
                    foundItems.addAll(radarTypeService.findAllByUserId(targetUser.getId(), false));
                }

                if (includeSharedByOthers == true)
                {
                    foundItems.addAll(radarTypeService.findAllSharedRadarTypesExcludeOwned(targetUser.getId()));
                }

                if (includeAssociated == true)
                {
                    foundItems.addAll(radarTypeService.findAllAssociatedRadarTypes(targetUser.getId()));
                }
            }
            else
            {
                foundItems = this.radarTypeService.findAllForPublishedRadars(targetUser.getId());
            }
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeMessage(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/public/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> getRadarTypesForPublishedRadars(@PathVariable Long radarUserId)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);
        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(targetUser!=null)
        {
            foundItems = this.radarTypeService.findAllForPublishedRadars(targetUser.getId());
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeMessage(radarTypeItem));
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody RadarTypeMessage addRadarType(@RequestBody RadarTypeMessage radarTypeMessage, @PathVariable Long radarUserId)
    {
        RadarTypeMessage retVal = null;

        if(radarTypeMessage != null)
        {
            RadarType radarType = radarTypeMessage.ConvertToRadarType();
            radarType = this.radarTypeService.update(radarType, this.getCurrentUser(), radarUserId);
            retVal = new RadarTypeMessage(radarType);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody RadarTypeMessage updateRadarType(@RequestBody RadarTypeMessage radarTypeMessage, @PathVariable Long radarUserId, @PathVariable Long radarTypeId)
    {
        RadarTypeMessage retVal = null;

        if(radarTypeMessage != null)
        {
            RadarType radarType = radarTypeMessage.ConvertToRadarType();
            radarType = this.radarTypeService.update(radarType, this.getCurrentUser(), radarUserId);
            retVal = new RadarTypeMessage(radarType);
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody boolean updateRadarType(@PathVariable Long radarUserId, @PathVariable Long radarTypeId)
    {
        return this.radarTypeService.delete(radarTypeId, this.getCurrentUser(), radarUserId);
    }

    @RequestMapping(value = "/User/{userId}/RadarType/{radarTypeId}/Publish", method = RequestMethod.PUT)
    public @ResponseBody boolean updateRadarTypeIsPublished(@RequestBody Map modelMap, @PathVariable Long userId, @PathVariable Long radarTypeId)
    {
        boolean retVal = false;
        boolean isPublished = Boolean.parseBoolean(modelMap.get("isPublished").toString());

        if(this.getCurrentUser().getId() == userId)
        {
            retVal = this.radarTypeService.publishRadarType(this.getCurrentUser(), radarTypeId, isPublished);
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
            retVal = this.radarTypeService.associatedRadarType(this.getCurrentUser(), radarTypeId, shouldAssociate);
        }

        return retVal;
    }
}
