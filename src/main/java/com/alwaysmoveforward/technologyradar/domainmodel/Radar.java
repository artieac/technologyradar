package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
public class Radar implements Serializable
{
    private Long id;
    private String name;
    private Date assessmentDate;
    private String creator;
    private RadarUser radarUser;
    private List<RadarItem> radarItems;
    private boolean isPublished;

    public Radar()
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

    public List<RadarItem> getRadarItems() { return radarItems;}
    public void setRadarItems(List<RadarItem> value) { this.radarItems = value;}

    public RadarUser getRadarUser() { return this.radarUser;}
    public void setRadarUser(RadarUser value) { this.radarUser = value;}

    public boolean getIsPublished(){ return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}


    public void addRadarItem(RadarItem newAssessmentItem)
    {
        if(this.radarItems == null)
        {
            this.radarItems = new ArrayList<RadarItem>();
        }

        this.radarItems.add(newAssessmentItem);
    }

    public void addRadarItem(Technology targetTechnology, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        if (targetTechnology != null)
        {
            boolean alreadyIncluded = false;

            for (int i = 0; i < this.getRadarItems().size(); i++)
            {
                if (this.getRadarItems().get(i).getTechnology().getId() == targetTechnology.getId())
                {
                    alreadyIncluded = true;
                    break;
                }
            }

            if (alreadyIncluded == false)
            {
                RadarItem newItem = new RadarItem();
                newItem.setDetails(assessmentDetails);
                newItem.setRadarRing(radarRing);
                newItem.setTechnology(targetTechnology);
                newItem.setConfidenceFactor(confidenceLevel);
                this.getRadarItems().add(newItem);
            }
        }
    }

    public void updateRadarItem(Long assessmentItemId, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        for(int i = 0; i < this.radarItems.size(); i++)
        {
            RadarItem currentItem = this.radarItems.get(i);

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

        if(this.radarItems != null)
        {
            for(int i = 0; i < this.radarItems.size(); i++)
            {
                if(this.radarItems.get(i).getId() == assessmentItemId){
                    this.radarItems.remove(i);
                    break;
                }
            }
        }
    }
}