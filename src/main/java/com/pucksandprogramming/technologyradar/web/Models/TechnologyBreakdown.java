package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/26/2016.
 */
public class TechnologyBreakdown
{
    Technology targetTechnology;
    List<TechnologyBreakdownItem> userItems;
    List<TechnologyBreakdownItem> otherUsersItems;

    public TechnologyBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public List<TechnologyBreakdownItem> getUserItems() { return this.userItems;}

    public List<TechnologyBreakdownItem> getOtherUsersItems() { return this.otherUsersItems;}

    public void addTechnologyAssessment(Radar radarInstance, RadarUser currentUser)
    {
        if(radarInstance.getRadarItems() != null)
        {
            for(int i = 0; i < radarInstance.getRadarItems().size(); i++)
            {
                if (radarInstance.getRadarItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addTechnologyAssessmentItem(radarInstance, currentUser, radarInstance.getRadarItems().get(i));
                    break;
                }
            }
        }
    }

    public void addTechnologyAssessmentItem(Radar assessment, RadarUser currentUser, RadarItem assessmentItem)
    {
        if(this.userItems == null)
        {
            this.userItems = new ArrayList<TechnologyBreakdownItem>();
        }

        if(this.otherUsersItems == null)
        {
            this.otherUsersItems = new ArrayList<TechnologyBreakdownItem>();
        }

        TechnologyBreakdownItem newItem = new TechnologyBreakdownItem();
        newItem.setAssessmentId(assessment.getId());
        newItem.setAssessmentName(assessment.getName());
        newItem.setAssessmentDate(assessment.getAssessmentDate());
        newItem.setAssessmentUser(assessment.getRadarUser());
        newItem.setAssessmentRing(assessmentItem.getRadarRing());
        newItem.setAssessmentDetails(assessmentItem.getDetails());

        if(assessment.getRadarUser().getId()==currentUser.getId())
        {
            this.userItems.add(newItem);
        }
        else
        {
            this.otherUsersItems.add(newItem);
        }
    }
}
