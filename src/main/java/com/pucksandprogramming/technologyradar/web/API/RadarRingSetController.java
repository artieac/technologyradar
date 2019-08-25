package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarRingService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarRingSetViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RadarRingSetController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarRingSetController.class);

    @Autowired
    RadarUserService radarUserService;

    @Autowired
    RadarRingService radarRingService;

    @GetMapping(value = {"/User/{radarUserId}/RadarRingSets", "/public/User/{radarUserId}/RadarRingSets"}, produces = "application/json")
    public @ResponseBody
    List<RadarRingSetViewModel> getByUserId(@PathVariable Long radarUserId)
    {
        List<RadarRingSetViewModel> retVal = new ArrayList<RadarRingSetViewModel>();

        try
        {
            RadarUser targetUser = this.radarUserService.findOne(radarUserId);
            List<RadarRingSet> foundItems = new ArrayList<RadarRingSet>();

            if (targetUser != null)
            {
                foundItems = radarRingService.findByUserId(radarUserId);
            }

            for (RadarRingSet foundItem : foundItems)
            {
                retVal.add(new RadarRingSetViewModel(foundItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/RadarRingSet", produces = "application/json")
    public @ResponseBody
    RadarRingSetViewModel addRadarType(@RequestBody RadarRingSetViewModel viewModel, @PathVariable Long radarUserId)
    {
        RadarRingSetViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarRingSet radarRingSet = viewModel.ConvertToRadarRingSet();
                    radarRingSet = radarRingService.update(radarRingSet, radarUserId);
                    retVal = new RadarRingSetViewModel(radarRingSet);
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/RadarRingSet/{radarRingSetId}", produces = "application/json")
    public @ResponseBody
    RadarRingSetViewModel updateRadarType(@RequestBody RadarRingSetViewModel viewModel, @PathVariable Long radarUserId, @PathVariable Long radarRingSetId)
    {
        RadarRingSetViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarRingSet radarRingSet = viewModel.ConvertToRadarRingSet();
                    radarRingSet = radarRingService.update(radarRingSet, radarUserId);
                    retVal = new RadarRingSetViewModel(radarRingSet);
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }
}
