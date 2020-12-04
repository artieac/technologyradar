package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/20/2016.
 */
@Controller
@RequestMapping("/api")
public class RadarConfigurationController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RadarConfigurationController.class);

    private final DiagramConfigurationService radarSetupService;
    private final RadarService radarService;

    @Autowired
    public RadarConfigurationController(DiagramConfigurationService diagramConfigurationService,
                                        RadarService radarService){
        this.radarSetupService = diagramConfigurationService;
        this.radarService = radarService;
    }
    @GetMapping(value = "/radar/{radarId}/rings", produces = "application/json")
    public @ResponseBody List<RadarRing> getRadarRings(@PathVariable Long radarId) {
        List<RadarRing> retVal = new ArrayList<RadarRing>();

        try {
            Optional<Radar> targetRadar = this.radarService.findById(radarId);

            if (targetRadar.isPresent()) {
                retVal = targetRadar.get().getRadarTemplate().getRadarRings();
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/radar/{radarId}/categories", produces = "application/json")
    public @ResponseBody List<RadarCategory> getRadarCategories(@PathVariable Long radarId) {
        List<RadarCategory> retVal = new ArrayList<RadarCategory>();

        try {
            Optional<Radar> targetRadar = this.radarService.findById(radarId);

            if (targetRadar.isPresent()) {
                retVal = targetRadar.get().getRadarTemplate().getRadarCategories();
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }
}
