package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by acorrea on 10/26/2016.
 */
public class TechnologyBreakdown
{
    List<AssessmentTeam> assessmentTeams;
    Technology targetTechnology;
    Hashtable<Long, List<TechnologyAssessmentItem>> teamAssessments;

    public TechnologyBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public List<AssessmentTeam> getAssessmentTeams() { return this.assessmentTeams;}

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public Hashtable<Long, List<TechnologyAssessmentItem>> getTeamAssessments() { return this.teamAssessments;}

    public void addTechnologyAssessment(TechnologyAssessment technologyAssessment)
    {
        if(technologyAssessment.getTechnologyAssessmentItems() != null)
        {
            for(int i = 0; i < technologyAssessment.getTechnologyAssessmentItems().size(); i++)
            {
                if (technologyAssessment.getTechnologyAssessmentItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(technologyAssessment.getAssessmentTeam(), technologyAssessment.getTechnologyAssessmentItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(AssessmentTeam team, TechnologyAssessmentItem assessmentItem)
    {
        if(this.assessmentTeams == null)
        {
            this.assessmentTeams = new ArrayList<>();
        }

        if(!this.assessmentTeams.contains(team))
        {
            this.assessmentTeams.add(team);
        }

        if(teamAssessments == null)
        {
            teamAssessments = new Hashtable<>();
        }

        if(teamAssessments.containsKey(team.getId()))
        {
            teamAssessments.get(team.getId()).add(assessmentItem);
        }
        else
        {
            List<TechnologyAssessmentItem> newList = new ArrayList<>();
            newList.add(assessmentItem);
            teamAssessments.put(team.getId(), newList);
        }
    }
}
