package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.services.AssessmentTeamService;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import com.alwaysmoveforward.technologyradar.web.Models.RadarStatePresentation;
import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.web.Models.Quadrant;
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

    @RequestMapping(value = "/states", produces = "application/json")
    public @ResponseBody List<RadarState> getRadarStates()
    {
        List<RadarState> retVal = this.radarSetupService.getRadarStates();

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

        Iterable<RadarState> radarStates = this.radarSetupService.getRadarStates();
        retVal.addRadarArcs(radarStates);
        Hashtable<Long, RadarStatePresentation> radarStateLookup = new Hashtable<Long, RadarStatePresentation>();
        List<RadarStatePresentation> radarStatePresentations = retVal.getRadarArcs();

        for(RadarStatePresentation radarState : radarStatePresentations)
        {
            radarStateLookup.put(radarState.getRadarState().getId(), radarState);
        }

        Iterable<RadarCategory> radarCategories = this.radarSetupService.getRadarCategories();
        Hashtable<Long, Quadrant> quadrantLookup = new Hashtable<Long, Quadrant>();

        Integer quadrantStart = 0;

        for(RadarCategory radarCategory : radarCategories)
        {
            Quadrant newQuadrant = new Quadrant(radarCategory, quadrantStart, retVal.getWidth(), retVal.getHeight());
            quadrantLookup.put(radarCategory.getId(), newQuadrant);
            retVal.getQuadrants().add(newQuadrant);
            quadrantStart += 90;
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
                Quadrant targetQuadrant = quadrantLookup.get(assessmentItem.getRadarCategory().getId());

                if(targetQuadrant != null)
                {
                    targetQuadrant.addItem(radarStateLookup.get(assessmentItem.getRadarState().getId()), assessmentItem);
                }
            }
        }


        for(int i = 0; i < retVal.getQuadrants().size(); i++){
            retVal.getQuadrants().get(i).defaultEmptyQuadrantStates(radarStatePresentations, technolgyAssessmentService.createDefaultTechnology());
        }

        return retVal;
    }
    @RequestMapping(value = "/team/{teamId}/assessment/{assessmentId}", produces = "application/json")
    public @ResponseBody DiagramPresentation getTeamAssessment(@PathVariable Long teamId, @PathVariable Long assessmentId)
    {
        DiagramPresentation retVal = new DiagramPresentation(1000,1200, 100);

        Iterable<RadarState> radarStates = this.radarSetupService.getRadarStates();
        retVal.addRadarArcs(radarStates);
        Hashtable<Long, RadarStatePresentation> radarStateLookup = new Hashtable<Long, RadarStatePresentation>();
        List<RadarStatePresentation> radarStatePresentations = retVal.getRadarArcs();

        for(RadarStatePresentation radarState : radarStatePresentations)
        {
            radarStateLookup.put(radarState.getRadarState().getId(), radarState);
        }

        Iterable<RadarCategory> radarCategories = this.radarSetupService.getRadarCategories();
        Hashtable<Long, Quadrant> quadrantLookup = new Hashtable<Long, Quadrant>();

        Integer quadrantStart = 0;

        for(RadarCategory radarCategory : radarCategories)
        {
            Quadrant newQuadrant = new Quadrant(radarCategory, quadrantStart, retVal.getWidth(), retVal.getHeight());
            quadrantLookup.put(radarCategory.getId(), newQuadrant);
            retVal.getQuadrants().add(newQuadrant);
            quadrantStart += 90;
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
                Quadrant targetQuadrant = quadrantLookup.get(assessmentItem.getRadarCategory().getId());

                if(targetQuadrant != null)
                {
                    targetQuadrant.addItem(radarStateLookup.get(assessmentItem.getRadarState().getId()), assessmentItem);
                }
            }
        }

        for(int i = 0; i < retVal.getQuadrants().size(); i++){
            retVal.getQuadrants().get(i).defaultEmptyQuadrantStates(radarStatePresentations, technolgyAssessmentService.createDefaultTechnology());
        }

        return retVal;
    }

    @RequestMapping(value = "/team/{teamId}/assessment/{assessmentId}/additem", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long teamId, @PathVariable Long assessmentId)
    {
        this.technolgyAssessmentService.addRadarItem(teamId, assessmentId, modelMap.get("technologyName").toString(), new Long((Integer)modelMap.get("radarState")), new Long((Integer)modelMap.get("radarCategory")));
        return this.generateDiagramData(teamId, assessmentId);
    }
}
