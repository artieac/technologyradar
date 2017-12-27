package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by acorrea on 10/26/2016.
 */
public class TechnologyBreakdown
{
    Technology targetTechnology;
    Hashtable<Long, List<TechnologyAssessmentItem>> userAssessments;

    public TechnologyBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public Hashtable<Long, List<TechnologyAssessmentItem>> getTeamAssessments() { return this.userAssessments;}

    public void addTechnologyAssessment(TechnologyAssessment technologyAssessment)
    {
        if(technologyAssessment.getTechnologyAssessmentItems() != null)
        {
            for(int i = 0; i < technologyAssessment.getTechnologyAssessmentItems().size(); i++)
            {
                if (technologyAssessment.getTechnologyAssessmentItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(technologyAssessment.getRadarUser(), technologyAssessment.getTechnologyAssessmentItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(RadarUser radarUser, TechnologyAssessmentItem assessmentItem)
    {
        if(userAssessments == null)
        {
            userAssessments = new Hashtable<>();
        }

        if(userAssessments.containsKey(radarUser.getId()))
        {
            userAssessments.get(radarUser.getId()).add(assessmentItem);
        }
        else
        {
            List<TechnologyAssessmentItem> newList = new ArrayList<>();
            newList.add(assessmentItem);
            userAssessments.put(radarUser.getId(), newList);
        }
    }
}
