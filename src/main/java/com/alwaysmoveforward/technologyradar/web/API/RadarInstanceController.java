package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarInstanceService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import com.alwaysmoveforward.technologyradar.web.Models.Quadrant;
import com.alwaysmoveforward.technologyradar.web.Models.RadarRingPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 12/24/2017.
 */
@Controller
@RequestMapping("/api")
public class RadarInstanceController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarInstanceController.class);

    @Autowired
    private RadarInstanceService radarInstanceService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @RequestMapping(value = {"/User/{radarUserId}/Radars", "/public/User/{radarUserId}/Radars"}, method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<RadarInstance> getRadarUserAssessments(@PathVariable Long radarUserId)
    {
        return this.radarInstanceService.findByRadarUserId(radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar", method = RequestMethod.POST)
    public @ResponseBody List<RadarInstance> addTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        this.radarInstanceService.addRadarUserAssessment(radarUserId, modelMap.get("name").toString());
        return this.radarInstanceService.findByRadarUserId(radarUserId);
    }

    @RequestMapping(value = {"/User/{radarUserId}/Radar/{radarId}", "/public/User/{radarUserId}/Radar/{radarId}"}, produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody DiagramPresentation getTeamAssessment(@PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        DiagramPresentation retVal = this.generateDiagramData(radarUserId, radarId);
        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}", method = RequestMethod.PUT)
    public @ResponseBody List<RadarInstance> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        this.radarInstanceService.updateUserRadar(radarUserId, radarId, modelMap.get("name").toString());
        return this.radarInstanceService.findByRadarUserId(radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteTechnologyAssessmentItem(@PathVariable Long radarId, @PathVariable Long radarItemId, @PathVariable Long radarUserId)
    {
        return this.radarInstanceService.deleteRadarItem(radarId, radarItemId, radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        String technologyName = modelMap.get("technologyName").toString();
        Long radarCategory = Long.parseLong(modelMap.get("radarCategory").toString());
        String technologyUrl = modelMap.get("url").toString();
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();
        Technology targetTechnology = null;

        if(modelMap.get("technologyId")!=null)
        {
            Long technologyId = new Long((Integer)modelMap.get("technologyId"));
            targetTechnology = this.radarInstanceService.findTechnologyById(technologyId);
        }

        if(targetTechnology!=null)
        {
            this.radarInstanceService.addRadarItem(this.getCurrentUser(), radarId, targetTechnology, radarRing, confidenceLevel, assessmentDetails);
        }
        else
        {
            this.radarInstanceService.addRadarItem(this.getCurrentUser(), radarId, technologyName, technologyUrl, radarCategory, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.generateDiagramData(radarUserId, radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/item/{radarItemId}", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation updateRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId, @PathVariable Long radarItemId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        if(radarId > 0 && radarItemId > 0 && this.getCurrentUser().getId() == radarUserId);
        {
            this.radarInstanceService.updateRadarItem(radarId, radarItemId, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.generateDiagramData(radarUserId, radarId);
    }

    private DiagramPresentation generateDiagramData(Long radarUserId, Long radarId)
    {
        DiagramPresentation retVal = new DiagramPresentation(1000,1200, 100);

        Iterable<RadarRing> radarRings = this.radarSetupService.getRadarRings();
        retVal.addRadarArcs(radarRings);
        Hashtable<Long, RadarRingPresentation> radarRingLookup = new Hashtable<Long, RadarRingPresentation>();
        List<RadarRingPresentation> radarRingPresentations = retVal.getRadarArcs();

        for(RadarRingPresentation radarRing : radarRingPresentations)
        {
            radarRingLookup.put(radarRing.getRadarRing().getId(), radarRing);
        }

        Iterable<RadarCategory> radarCategories = this.radarSetupService.getRadarCategories();
        Hashtable<Long, Quadrant> quadrantLookup = new Hashtable<Long, Quadrant>();

        for(RadarCategory radarCategory : radarCategories)
        {
            Quadrant newQuadrant = new Quadrant(radarCategory, retVal.getWidth(), retVal.getHeight(), radarRingPresentations);
            quadrantLookup.put(radarCategory.getId(), newQuadrant);
            retVal.getQuadrants().add(newQuadrant);
        }

        RadarInstance radarInstance = radarInstanceService.findById(radarId);

        if(radarInstance == null)
        {
            RadarUser radarUser = this.radarUserService.findOne(radarUserId);

            if(radarUser != null)
            {
                radarInstance = radarInstanceService.createDefault(radarUser);
            }
        }

        retVal.setRadarInstanceDetails(radarInstance);

        logger.debug(radarInstance);
        logger.debug(radarInstance.getName());
        logger.debug(radarInstance.getRadarInstanceItems());
        logger.debug(radarInstance.getRadarInstanceItems().size());

        if(radarInstance.getRadarInstanceItems().size() > 0)
        {
            for(RadarInstanceItem assessmentItem : radarInstance.getRadarInstanceItems())
            {
                Quadrant targetQuadrant = quadrantLookup.get(assessmentItem.getTechnology().getRadarCategory().getId());

                if(targetQuadrant != null)
                {
                    targetQuadrant.addItem(radarRingLookup.get(assessmentItem.getRadarRing().getId()), assessmentItem);
                }
            }
        }

        for(int i = 0; i < retVal.getQuadrants().size(); i++)
        {
            retVal.getQuadrants().get(i).evenlyDistributeItems();
        }

        return retVal;
    }

}
