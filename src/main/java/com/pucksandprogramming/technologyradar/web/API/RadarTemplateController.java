package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarRingService;
import com.pucksandprogramming.technologyradar.services.RadarTemplateService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarRingSetViewModel;
import com.pucksandprogramming.technologyradar.web.Models.RadarTemplateViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RadarTemplateController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarTemplateController.class);

    @Autowired
    RadarUserService radarUserService;

    @Autowired
    RadarTemplateService radarTemplateService;

    @GetMapping(value = {"/User/{radarUserId}/RadarTemplates", "/public/User/{radarUserId}/RadarTemplates"}, produces = "application/json")
    public @ResponseBody
    List<RadarTemplateViewModel> getByUserId(@PathVariable Long radarUserId)
    {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try
        {
            RadarUser targetUser = this.radarUserService.findOne(radarUserId);
            List<RadarTemplate> foundItems = new ArrayList<RadarTemplate>();

            if (targetUser != null)
            {
                foundItems = radarTemplateService.findByUserId(radarUserId);
            }

            for (RadarTemplate foundItem : foundItems)
            {
                retVal.add(new RadarTemplateViewModel(foundItem));
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/RadarTemplate", produces = "application/json")
    public @ResponseBody
    RadarTemplateViewModel addRadarType(@RequestBody RadarTemplateViewModel viewModel, @PathVariable Long radarUserId)
    {
        RadarTemplateViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarTemplate radarTemplate = viewModel.convertToRadarTemplate();
                    radarTemplate = radarTemplateService.update(radarTemplate, radarUserId);
                    retVal = new RadarTemplateViewModel(radarTemplate);
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/RadarTemplate/{radarTemplateId}", produces = "application/json")
    public @ResponseBody
    RadarTemplateViewModel updateRadarType(@RequestBody RadarTemplateViewModel viewModel,
                                          @PathVariable Long radarUserId,
                                          @PathVariable Long radarTemplateId)
    {
        RadarTemplateViewModel retVal = null;

        try
        {
            if (viewModel != null)
            {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null)
                {
                    RadarTemplate radarTemplate = viewModel.convertToRadarTemplate();
                    radarTemplate = radarTemplateService.update(radarTemplate, radarUserId);
                    retVal = new RadarTemplateViewModel(radarTemplate);
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

