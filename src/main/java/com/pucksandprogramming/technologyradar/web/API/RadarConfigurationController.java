package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarServiceFactory;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/20/2016.
 */
@Controller
@RequestMapping("/api")
public class RadarConfigurationController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarConfigurationController.class);

    @Autowired
    DiagramConfigurationService radarSetupService;

    @Autowired
    RadarServiceFactory radarServiceFactory;

    @GetMapping(value = "/radar/{radarId}/rings", produces = "application/json")
    public @ResponseBody List<RadarRing> getRadarRings(@PathVariable Long radarId)
    {
        List<RadarRing> retVal = new ArrayList<RadarRing>();

        try
        {
            Radar targetRadar = this.radarServiceFactory.getMostRecent().findById(radarId);

            if (targetRadar != null)
            {
                retVal = targetRadar.getRadarType().getRadarRings();
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/radar/{radarId}/categories", produces = "application/json")
    public @ResponseBody List<RadarCategory> getRadarCategories(@PathVariable Long radarId)
    {
        List<RadarCategory> retVal = new ArrayList<RadarCategory>();

        try
        {
            Radar targetRadar = this.radarServiceFactory.getMostRecent().findById(radarId);

            if (targetRadar != null)
            {
                retVal = targetRadar.getRadarType().getRadarCategories();
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }
}
