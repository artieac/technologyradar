package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
public class RadarInstance implements Serializable
{
    private Long id;

    private String name;

    private Date assessmentDate;

    private String creator;

    private RadarUser radarUser;

    private List<RadarInstanceItem> radarInstanceItems;

    public RadarInstance()
    {
        this.assessmentDate = new Date();
    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}

    public void setName(String value) { this.name = value;}

    public Date getAssessmentDate() { return assessmentDate;}

    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public String getCreator() { return creator;}

    public void setCreator(String value) { this.creator = value;}

    public List<RadarInstanceItem> getRadarInstanceItems() { return radarInstanceItems;}

    public void setRadarInstanceItems(List<RadarInstanceItem> value) { this.radarInstanceItems = value;}

    public RadarUser getRadarUser() { return this.radarUser;}

    public void setRadarUser(RadarUser value) { this.radarUser = value;}

    public void addRadarItem(RadarInstanceItem newAssessmentItem)
    {
        if(this.radarInstanceItems == null)
        {
            this.radarInstanceItems = new ArrayList<RadarInstanceItem>();
        }

        this.radarInstanceItems.add(newAssessmentItem);
    }

    public void updateRadarItem(Long assessmentItemId, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        for(int i = 0; i < this.radarInstanceItems.size(); i++)
        {
            RadarInstanceItem currentItem = this.radarInstanceItems.get(i);

            if(currentItem.getId()==assessmentItemId)
            {
                currentItem.setRadarRing(radarRing);
                currentItem.setConfidenceFactor(confidenceLevel);
                currentItem.setDetails(assessmentDetails);
                break;
            }
        }
    }

    public void removeRadarItem(Long assessmentItemId){

        if(this.radarInstanceItems != null)
        {
            for(int i = 0; i < this.radarInstanceItems.size(); i++)
            {
                if(this.radarInstanceItems.get(i).getId() == assessmentItemId){
                    this.radarInstanceItems.remove(i);
                    break;
                }
            }
        }
    }
}