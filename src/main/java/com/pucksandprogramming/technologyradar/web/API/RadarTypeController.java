package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
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

    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    private RadarUserService radarUserService;

    @RequestMapping(value = "/User/{radarUserId}/RadarTypes", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> getRadarTypesByUserId(@PathVariable Long radarUserId)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        RadarUser targetUser = radarUserService.findOne(radarUserId);

        if(targetUser!=null)
        {
            for(RadarType radarTypeItem : targetUser.getRadarTypes())
            {
                retVal.add(new RadarTypeMessage(radarTypeItem));
            }
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> addRadarType(@RequestBody RadarType radarType, @PathVariable Long radarUserId)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        if(radarType != null)
        {
            RadarUser targetUser = radarUserService.findOne(radarUserId);

            if(targetUser!=null) {
                if (this.getCurrentUser().getId() == targetUser.getId()) {
                    radarType.setRadarUser(this.getCurrentUser());
                    this.getCurrentUser().addRadarType(radarType);
                    this.radarUserService.updateUser(this.getCurrentUser());
                }
            }

            for(RadarType radarTypeItem : this.getCurrentUser().getRadarTypes()) {
                retVal.add(new RadarTypeMessage(radarTypeItem));
            }
        }

        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/RadarType/{radarTypeId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody List<RadarTypeMessage> updateRadarType(@RequestBody RadarTypeMessage radarTypeMessage, @PathVariable Long radarUserId, @PathVariable Long radarTypeId)
    {
        List<RadarTypeMessage> retVal = new ArrayList<RadarTypeMessage>();

        if(radarTypeMessage != null)
        {
            RadarUser targetUser = radarUserService.findOne(radarUserId);

            if(targetUser!=null) {
                if (this.getCurrentUser().getId() == targetUser.getId()) {
                    RadarType radarType = radarTypeMessage.ConvertToRadarType();
                    radarType.setRadarUser(this.getCurrentUser());
                    this.getCurrentUser().updateRadarType(radarType);
                    this.radarUserService.updateUser(this.getCurrentUser());
                }
            }

            for(RadarType radarTypeItem : this.getCurrentUser().getRadarTypes()) {
                retVal.add(new RadarTypeMessage(radarTypeItem));
            }
        }

        return retVal;
    }
}
