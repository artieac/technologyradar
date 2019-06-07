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
    public @ResponseBody List<RadarTypeMessage> getRadarTypesByUserId()
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
                                                                      @RequestParam(name="includeOthers", required = false, defaultValue = "false") boolean includeOthers)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);

        if(targetUser!=null)
        {
            List<RadarType> userRadarTypes = new ArrayList<RadarType>();

            if(includeOwned==true)
            {
                userRadarTypes.addAll(radarTypeService.findAllByUserId(targetUser.getId(), false));
            }
            else
            {
                if (includeSharedByOthers == true)
                {
                    userRadarTypes.addAll(radarTypeService.findAllSharedRadarTypesExcludeOwned(targetUser.getId()));
                }
            }

            if(includeOthers == true)
            {
                userRadarTypes.addAll(radarTypeService.findOthersRadarTypes(targetUser.getId()));
            }

            for (RadarType radarTypeItem : userRadarTypes)
            {
                retVal.add(new RadarTypeMessage(radarTypeItem));
            }
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
            retVal = this.radarTypeService.publishRadarType(userId, radarTypeId, isPublished);
        }

        return retVal;
    }

}
