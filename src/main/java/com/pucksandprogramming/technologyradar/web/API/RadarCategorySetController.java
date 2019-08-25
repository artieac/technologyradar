package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarCategoryService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarCategorySetViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RadarCategorySetController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarCategorySetController.class);

    @Autowired
    RadarUserService radarUserService;

    @Autowired
    RadarCategoryService radarCategoryService;

    @GetMapping(value = {"/User/{radarUserId}/RadarCategorySets", "/public/User/{radarUserId}/RadarCategorySets"}, produces = "application/json")
    public @ResponseBody
    List<RadarCategorySetViewModel> getByUserId(@PathVariable Long radarUserId)
    {
        List<RadarCategorySetViewModel> retVal = new ArrayList<RadarCategorySetViewModel>();

        try
        {
            RadarUser targetUser = this.radarUserService.findOne(radarUserId);
            List<RadarCategorySet> foundItems = new ArrayList<RadarCategorySet>();

            if (targetUser != null)
            {
                foundItems = radarCategoryService.findByUserId(radarUserId);
            }

            for (RadarCategorySet foundItem : foundItems)
            {
                retVal.add(new RadarCategorySetViewModel(foundItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/RadarCategorySet", produces = "application/json")
    public @ResponseBody
    RadarCategorySetViewModel addRadarCategorySet(@RequestBody RadarCategorySetViewModel viewModel, @PathVariable Long radarUserId)
    {
        RadarCategorySetViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarCategorySet radarCategorySet = viewModel.ConvertToRadarCategorySet();
                    radarCategorySet = radarCategoryService.update(radarCategorySet, radarUserId);
                    retVal = new RadarCategorySetViewModel(radarCategorySet);
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/RadarCategorySet/{radarRingSetId}", produces = "application/json")
    public @ResponseBody
    RadarCategorySetViewModel updateRadarCategorySet(@RequestBody RadarCategorySetViewModel viewModel, @PathVariable Long radarUserId, @PathVariable Long radarRingSetId)
    {
        RadarCategorySetViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarCategorySet radarCategorySet = viewModel.ConvertToRadarCategorySet();
                    radarCategorySet = radarCategoryService.update(radarCategorySet, radarUserId);
                    retVal = new RadarCategorySetViewModel(radarCategorySet);
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

