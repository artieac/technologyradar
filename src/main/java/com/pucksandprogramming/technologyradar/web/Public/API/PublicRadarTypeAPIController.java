package com.pucksandprogramming.technologyradar.web.Public.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.services.RadarType.PublicRadarTypeService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarTypeViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("PublicAPIRadarSubjectController")
@RequestMapping("/api/public")
public class PublicRadarTypeAPIController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(PublicRadarTypeAPIController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    PublicRadarTypeService radarTypeService;

    @RequestMapping(value = "/RadarTypes/Shared", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarTypeViewModel> getSharedRadarTypes(@RequestParam(name="ownedBy", required = false, defaultValue = "-1") Long ownerUserId,
                                                                      @RequestParam(name="excludeUser", required = false, defaultValue = "-1") Long excludeUserId)
    {
        List<RadarTypeViewModel> retVal = new ArrayList<RadarTypeViewModel>();

        List<RadarType> foundItems = new ArrayList<RadarType>();

        if(ownerUserId != null && ownerUserId > 0)
        {
            foundItems = this.radarTypeService.findSharedRadarTypesByUser(ownerUserId);
        }
        else if(excludeUserId != null && excludeUserId > 0)
        {
            foundItems = this.radarTypeService.findSharedRadarTypesExcludeUser(excludeUserId);
        }
        else
        {
            foundItems = this.radarTypeService.findSharedRadarTypes();
        }

        for (RadarType radarTypeItem : foundItems)
        {
            retVal.add(new RadarTypeViewModel(radarTypeItem));
        }

        return retVal;
    }
}
