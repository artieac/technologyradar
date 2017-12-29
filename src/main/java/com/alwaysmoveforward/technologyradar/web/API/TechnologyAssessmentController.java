package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarUserService;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import com.alwaysmoveforward.technologyradar.web.Models.Quadrant;
import com.alwaysmoveforward.technologyradar.web.Models.QuadrantItem;
import com.alwaysmoveforward.technologyradar.web.Models.RadarRingPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by acorrea on 12/24/2017.
 */
@Controller
@RequestMapping("/api")
public class TechnologyAssessmentController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(TechnologyAssessmentController.class);

    @Autowired
    private TechnologyAssessmentService technologyAssessmentService;

    @Autowired
    private DiagramConfigurationService radarSetupService;

    @RequestMapping(value = "/User/{id}/TechnologyAssessments", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<TechnologyAssessment> getRadarUserAssessments(@PathVariable Long id)
    {
        return this.technologyAssessmentService.findByRadarUserId(id);
    }

    @RequestMapping(value = "/User/{radarUserId}/TechnologyAssessment", method = RequestMethod.POST)
    public @ResponseBody List<TechnologyAssessment> addTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId)
    {
        this.technologyAssessmentService.addRadarUserAssessment(radarUserId, modelMap.get("name").toString());
        return this.technologyAssessmentService.findByRadarUserId(radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/TechnologyAssessment/{assessmentId}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody DiagramPresentation getTeamAssessment(@PathVariable Long radarUserId, @PathVariable Long assessmentId)
    {
        DiagramPresentation retVal = this.generateDiagramData(radarUserId, assessmentId);
        return retVal;
    }

    @RequestMapping(value = "/User/{radarUserId}/TechnologyAssessment/{assessmentId}", method = RequestMethod.PUT)
    public @ResponseBody List<TechnologyAssessment> updateTechnologyAssessment(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long assessmentId)
    {
        this.technologyAssessmentService.updateUserAssessment(radarUserId, assessmentId, modelMap.get("name").toString());
        return this.technologyAssessmentService.findByRadarUserId(radarUserId);
    }

    private DiagramPresentation generateDiagramData(Long radarUserId, Long assessmentId)
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

        TechnologyAssessment technologyAssessment = technologyAssessmentService.findById(assessmentId);

        if(technologyAssessment == null)
        {
            RadarUser radarUser = this.radarUserService.findOne(radarUserId);

            if(radarUser != null)
            {
                technologyAssessment = technologyAssessmentService.createDefault(radarUser);
            }
        }

        logger.debug(technologyAssessment);
        logger.debug(technologyAssessment.getName());
        logger.debug(technologyAssessment.getTechnologyAssessmentItems());
        logger.debug(technologyAssessment.getTechnologyAssessmentItems().size());

        if(technologyAssessment.getTechnologyAssessmentItems().size() > 0)
        {
            for(TechnologyAssessmentItem assessmentItem : technologyAssessment.getTechnologyAssessmentItems())
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


    @RequestMapping(value = "/User/{radarUserId}/TechnologyAssessment/{assessmentId}/Item", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long assessmentId)
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
            targetTechnology = this.technologyAssessmentService.findTechnologyById(technologyId);
        }

        if(targetTechnology!=null)
        {
            this.technologyAssessmentService.addRadarItem(this.getCurrentUser(), assessmentId, targetTechnology, radarRing, confidenceLevel, assessmentDetails);
        }
        else
        {
            this.technologyAssessmentService.addRadarItem(this.getCurrentUser(), assessmentId, technologyName, technologyUrl, radarCategory, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.generateDiagramData(radarUserId, assessmentId);
    }

    @RequestMapping(value = "/User/{radarUserId}/TechnologyAssessment/{assessmentId}/item/{assessmentItemId}", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation updateRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long assessmentId, @PathVariable Long assessmentItemId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        if(assessmentId > 0 && assessmentItemId > 0 && this.getCurrentUser().getId() == radarUserId);
        {
            this.technologyAssessmentService.updateAssessmentItem(assessmentId, assessmentItemId, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.generateDiagramData(radarUserId, assessmentId);
    }}
