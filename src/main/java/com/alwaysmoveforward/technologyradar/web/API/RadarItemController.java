package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by acorrea on 1/10/2018.
 */
@Controller
@RequestMapping("/api")
public class RadarItemController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarItemController.class);

    @Autowired
    RadarService radarService;

    @Autowired
    DiagramConfigurationService diagramConfigurationService;

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}", method = RequestMethod.DELETE)
    public @ResponseBody
    boolean deleteTechnologyAssessmentItem(@PathVariable Long radarId, @PathVariable Long radarItemId, @PathVariable Long radarUserId)
    {
        return this.radarService.deleteRadarItem(radarId, radarItemId, radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item", method = RequestMethod.POST)
    public @ResponseBody
    DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        RadarUser radarUser = radarUserService.findOne(radarUserId);

        if(radarUser != null && radarUser.getId() == this.getCurrentUser().getId())
        {
            Technology targetTechnology = null;

            if (modelMap.get("technologyId") != null)
            {
                Long technologyId = new Long((Integer) modelMap.get("technologyId"));
                targetTechnology = this.radarService.findTechnologyById(technologyId);

                this.radarService.addRadarItem(this.getCurrentUser(), radarId, targetTechnology, radarRing, confidenceLevel, assessmentDetails);
            } else
            {
                String technologyName = modelMap.get("technologyName").toString();
                Long radarCategory = Long.parseLong(modelMap.get("radarCategory").toString());
                String technologyUrl = modelMap.get("url").toString();

                this.radarService.addRadarItem(this.getCurrentUser(), radarId, technologyName, technologyUrl, radarCategory, radarRing, confidenceLevel, assessmentDetails);
            }
        }

        return this.diagramConfigurationService.generateDiagramData(this.getCurrentUser(), radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation updateRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId, @PathVariable Long radarItemId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        if(radarId > 0 && radarItemId > 0 && this.getCurrentUser().getId() == radarUserId);
        {
            this.radarService.updateRadarItem(radarId, radarItemId, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.diagramConfigurationService.generateDiagramData(this.getCurrentUser(), radarId);
    }


}
