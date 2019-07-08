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
public class RadarSubjectBreakdown
{
    Technology targetTechnology;
    List<RadarSubjectBreakdownItem> userItems;
    List<RadarSubjectBreakdownItem> otherUsersItems;

    public RadarSubjectBreakdown(Technology technology)
    {
        this.targetTechnology = technology;
    }

    public Technology getTargetTechnology() { return this.targetTechnology; }

    public List<RadarSubjectBreakdownItem> getUserItems() { return this.userItems;}

    public List<RadarSubjectBreakdownItem> getOtherUsersItems() { return this.otherUsersItems;}

    public void addOwnedRadarSubjectAssessment(Radar radarInstance)
    {
        if(radarInstance.getRadarItems() != null)
        {
            for(int i = 0; i < radarInstance.getRadarItems().size(); i++)
            {
                if (radarInstance.getRadarItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addRadarSubjectAssessmentItem(radarInstance, radarInstance.getRadarItems().get(i), true);
                    break;
                }
            }
        }
    }
    public void addPublishedRadarSubjectAssessment(Radar radarInstance)
    {
        if(radarInstance.getRadarItems() != null)
        {
            for(int i = 0; i < radarInstance.getRadarItems().size(); i++)
            {
                if (radarInstance.getRadarItems().get(i).getTechnology().getId() == this.targetTechnology.getId())
                {
                    this.addRadarSubjectAssessmentItem(radarInstance, radarInstance.getRadarItems().get(i), false);
                    break;
                }
            }
        }
    }

    public void addRadarSubjectAssessmentItem(Radar assessment, RadarItem assessmentItem, boolean isOwned)
    {
        if(this.userItems == null)
        {
            this.userItems = new ArrayList<RadarSubjectBreakdownItem>();
        }

        if(this.otherUsersItems == null)
        {
            this.otherUsersItems = new ArrayList<RadarSubjectBreakdownItem>();
        }

        RadarSubjectBreakdownItem newItem = new RadarSubjectBreakdownItem();
        newItem.setAssessmentId(assessment.getId());
        newItem.setAssessmentName(assessment.getName());
        newItem.setAssessmentDate(assessment.getAssessmentDate());
        newItem.setAssessmentUser(new UserViewModel(assessment.getRadarUser()));
        newItem.setAssessmentCategory(assessmentItem.getRadarCategory());
        newItem.setAssessmentRing(assessmentItem.getRadarRing());
        newItem.setAssessmentDetails(assessmentItem.getDetails());

        if(isOwned)
        {
            this.userItems.add(newItem);
        }
        else
        {
            this.otherUsersItems.add(newItem);
        }
    }
}
