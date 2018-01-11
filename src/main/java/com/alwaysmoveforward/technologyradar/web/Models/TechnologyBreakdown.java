package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.Radar;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarItem;

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

    public void addTechnologyAssessment(Radar radarInstance)
    {
        if(radarInstance.getRadarItems() != null)
        {
            for(int i = 0; i < radarInstance.getRadarItems().size(); i++)
            {
                if (radarInstance.getRadarItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(radarInstance, radarInstance.getRadarItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(Radar assessment, RadarItem assessmentItem)
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
