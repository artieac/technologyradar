package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.services.AssessmentTeamService;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.HomeController;
import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/api")
public class AssessmentTeams
{
    private static final Logger logger = Logger.getLogger(AssessmentTeams.class);

    @Autowired
    private AssessmentTeamService assessmentTeamService;

    @Autowired
    private TechnologyAssessmentService technologyAssessmentService;

    @RequestMapping("/assessmentteams")
    public @ResponseBody List<AssessmentTeam> getAllTeams()
    {
        return this.assessmentTeamService.getAssessmentTeams();
    }

    @RequestMapping(value = "/assessmentteam", method = RequestMethod.POST)
    public @ResponseBody List<AssessmentTeam> addTeam(@RequestBody Map modelMap)
    {
        this.assessmentTeamService.addTeam(modelMap.get("name").toString());
        return this.assessmentTeamService.getAssessmentTeams();
    }

    @RequestMapping(value = "/assessmentteam/{id}", method = RequestMethod.PUT)
    public @ResponseBody List<AssessmentTeam> updateTeam(@RequestBody Map modelMap, @PathVariable Long id)
    {
        this.assessmentTeamService.updateTeam(id, modelMap.get("name").toString());
        return this.assessmentTeamService.getAssessmentTeams();
    }

    @RequestMapping(value = "/assessmentteam/{id}/assessments", method = RequestMethod.GET)
    public @ResponseBody List<TechnologyAssessment> getTeamAssessments(@PathVariable Long id)
    {
        return this.technologyAssessmentService.findByTeamId(id);
    }

    @RequestMapping(value = "/assessmentteam/{teamId}/assessment", method = RequestMethod.POST)
    public @ResponseBody List<TechnologyAssessment> addTeamAssessment(@RequestBody Map modelMap, @PathVariable Long teamId)
    {
        this.technologyAssessmentService.addTeamAssessment(teamId, modelMap.get("name").toString());
        return this.technologyAssessmentService.findByTeamId(teamId);
    }

    @RequestMapping(value = "/assessmentteam/{teamId}/assessment/{assessmentId}", method = RequestMethod.PUT)
    public @ResponseBody List<TechnologyAssessment> updateTeamAssessment(@RequestBody Map modelMap, @PathVariable Long teamId, @PathVariable Long assessmentId)
    {
        this.technologyAssessmentService.updateTeamAssessment(teamId, assessmentId, modelMap.get("name").toString());
        return this.technologyAssessmentService.findByTeamId(teamId);
    }
}
