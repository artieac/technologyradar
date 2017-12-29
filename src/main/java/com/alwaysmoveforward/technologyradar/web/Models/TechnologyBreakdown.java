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
    List<TechnologyBreakdownItem> items;

    public TechnologyBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public List<TechnologyBreakdownItem> getItems() { return this.items;}

    public void addTechnologyAssessment(TechnologyAssessment technologyAssessment)
    {
        if(technologyAssessment.getTechnologyAssessmentItems() != null)
        {
            for(int i = 0; i < technologyAssessment.getTechnologyAssessmentItems().size(); i++)
            {
                if (technologyAssessment.getTechnologyAssessmentItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(technologyAssessment, technologyAssessment.getTechnologyAssessmentItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(TechnologyAssessment assessment, TechnologyAssessmentItem assessmentItem)
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
