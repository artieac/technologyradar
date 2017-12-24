package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.AssessmentTeamService;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.services.TechnologyService;
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
 * Created by acorrea on 10/20/2016.
 */
@Controller
@RequestMapping("/api/radar")
public class RadarController
{
    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    DiagramConfigurationService radarSetupService;

    @Autowired
    TechnologyAssessmentService technolgyAssessmentService;

    @Autowired
    AssessmentTeamService assessmentTeamService;

    @RequestMapping(value = "/teams", produces = "application/json")
    public @ResponseBody List<AssessmentTeam> getTeams()
    {
        List<AssessmentTeam> retVal = this.assessmentTeamService.getAssessmentTeams();

        return retVal;
    }

    @RequestMapping(value = "/rings", produces = "application/json")
    public @ResponseBody List<RadarRing> getRadarRings()
    {
        List<RadarRing> retVal = this.radarSetupService.getRadarRings();

        return retVal;
    }

    @RequestMapping(value = "/categories", produces = "application/json")
    public @ResponseBody List<RadarCategory> getRadarCategories()
    {
        List<RadarCategory> retVal = this.radarSetupService.getRadarCategories();

        return retVal;
    }

    @RequestMapping(value = "/team/{teamId}/assessments", produces = "application/json")
    public @ResponseBody List<TechnologyAssessment> getTeamAssessments(@PathVariable("teamId") Long teamId)
    {
        List<TechnologyAssessment> retVal = this.technolgyAssessmentService.getTeamAssessments(teamId);

        return retVal;
    }

    private DiagramPresentation generateDiagramData(Long teamId, Long assessmentId)
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
            Quadrant newQuadrant = new Quadrant(radarCategory, retVal.getWidth(), retVal.getHeight());
            quadrantLookup.put(radarCategory.getId(), newQuadrant);
            retVal.getQuadrants().add(newQuadrant);
        }

        TechnologyAssessment technologyAssessment = technolgyAssessmentService.findById(assessmentId);

        if(technologyAssessment == null)
        {
            AssessmentTeam assessmentTeam = this.assessmentTeamService.findOne(teamId);

            if(assessmentTeam != null)
            {
                technologyAssessment = technolgyAssessmentService.createDefault(assessmentTeam);
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

        return retVal;
    }

    @RequestMapping(value = "/team/{teamId}/assessment/{assessmentId}", produces = "application/json")
    public @ResponseBody DiagramPresentation getTeamAssessment(@PathVariable Long teamId, @PathVariable Long assessmentId)
    {
        DiagramPresentation retVal = this.generateDiagramData(teamId, assessmentId);
        return retVal;
    }

    @RequestMapping(value = "/team/{teamId}/assessment/{assessmentId}/additem", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long teamId, @PathVariable Long assessmentId)
    {
        String technologyName = modelMap.get("technologyName").toString();
        Long radarCategory = Long.parseLong(modelMap.get("radarCategory").toString());
        String technologyDescription = modelMap.get("technologyDescription").toString();
        String technologyUrl = "";
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();
        String evaluator = modelMap.get("evaluator").toString();
        Technology targetTechnology = null;

        if(modelMap.get("technologyId")!=null)
        {
            Long technologyId = new Long((Integer)modelMap.get("technologyId"));
            targetTechnology = this.technolgyAssessmentService.findTechnologyById(technologyId);
        }

        if(targetTechnology!=null)
        {
            this.technolgyAssessmentService.addRadarItem(teamId, assessmentId, targetTechnology, radarRing, confidenceLevel, assessmentDetails, evaluator);
        }
        else
        {
            this.technolgyAssessmentService.addRadarItem(teamId, assessmentId, technologyName, technologyDescription, technologyUrl, radarRing, confidenceLevel, radarCategory, assessmentDetails, evaluator);
        }

        return this.generateDiagramData(teamId, assessmentId);
    }

    @RequestMapping(value = "/team/{teamId}/assessment/{assessmentId}/item/{assessmentItemId}", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long teamId, @PathVariable Long assessmentId, @PathVariable Long assessmentItemId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();
        String evaluator = modelMap.get("evaluator").toString();

        if(assessmentId > 0 && assessmentItemId > 0)
        {
            this.technolgyAssessmentService.updateAssessmentItem(assessmentId, assessmentItemId, radarRing, confidenceLevel, assessmentDetails, evaluator);
        }

        return this.generateDiagramData(teamId, assessmentId);
    }
}
