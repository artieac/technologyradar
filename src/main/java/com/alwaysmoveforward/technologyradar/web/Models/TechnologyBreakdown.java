package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstance;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstanceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/26/2016.
 */
public class TechnologyBreakdown
{
    Technology targetTechnology;
    List<TechnologyBreakdownItem> items;

    public TechnologyBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public List<TechnologyBreakdownItem> getItems() { return this.items;}

    public void addTechnologyAssessment(RadarInstance radarInstance)
    {
        if(radarInstance.getRadarInstanceItems() != null)
        {
            for(int i = 0; i < radarInstance.getRadarInstanceItems().size(); i++)
            {
                if (radarInstance.getRadarInstanceItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(radarInstance, radarInstance.getRadarInstanceItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(RadarInstance assessment, RadarInstanceItem assessmentItem)
    {
        if(this.items == null)
        {
            this.items = new ArrayList<TechnologyBreakdownItem>();
        }

        TechnologyBreakdownItem newItem = new TechnologyBreakdownItem();
        newItem.setAssessmentId(assessment.getId());
        newItem.setAssessmentName(assessment.getName());
        newItem.setAssessmentDate(assessment.getAssessmentDate());
        newItem.setAssessmentUser(assessment.getRadarUser());
        newItem.setAssessmentRing(assessmentItem.getRadarRing());
        newItem.setAssessmentDetails(assessmentItem.getDetails());
        this.items.add(newItem);
    }
}
