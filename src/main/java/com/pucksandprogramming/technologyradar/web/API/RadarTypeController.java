package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
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

@Controller
@RequestMapping("/api")
public class RadarTypeController extends ControllerBase{

    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    private RadarTypeService radarTypeService;

    @RequestMapping(value = "/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> getRadarTypesByUserId(@PathVariable Long radarUserId)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);

        if(targetUser!=null)
        {
            List<RadarType> userRadarTypes = radarTypeService.findAllByUserId(targetUser.getId());

            for(RadarType radarTypeItem : userRadarTypes)
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

}
