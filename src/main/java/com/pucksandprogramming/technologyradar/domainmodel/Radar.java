package com.pucksandprogramming.technologyradar.domainmodel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
    private boolean isLocked;
    private RadarType radarType;

    public Radar()
    {
        this.assessmentDate = new Date();
        this.isPublished = false;
        this.isLocked = false;
    }

    public Long getId(){ return id;}
    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}
    public void setName(String value) { this.name = value;}

    public Date getAssessmentDate() { return assessmentDate;}
    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public String getFormattedAssessmentDate()
    {
        String pattern = "MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(this.assessmentDate);
    }

    public String getCreator() { return creator;}
    public void setCreator(String value) { this.creator = value;}

    public List<RadarItem> getRadarItems() { return radarItems;}
    public void setRadarItems(List<RadarItem> value) { this.radarItems = value;}

    public RadarUser getRadarUser() { return this.radarUser;}
    public void setRadarUser(RadarUser value) { this.radarUser = value;}

    public boolean getIsPublished(){ return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public boolean getIsLocked(){ return this.isLocked;}
    public void setIsLocked(boolean value){ this.isLocked = value;}

    public RadarType getRadarType() { return this.radarType;}
    public void setRadarType(RadarType value) { this.radarType = value;}

    public void addRadarItem(RadarItem newAssessmentItem)
    {
        if(this.radarItems == null)
        {
            this.radarItems = new ArrayList<RadarItem>();
        }

        this.radarItems.add(newAssessmentItem);
    }

    public void addRadarItem(Technology targetTechnology, RadarCategory radarCategory, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        if(this.isLocked == false)
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
                    newItem.setRadarCategory(radarCategory);
                    newItem.setRadarRing(radarRing);
                    newItem.setTechnology(targetTechnology);
                    newItem.setConfidenceFactor(confidenceLevel);
                    this.getRadarItems().add(newItem);
                }
            }
        }
    }

    public void updateRadarItem(Long assessmentItemId, RadarCategory radarCategory, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        if(this.isLocked == false)
        {
            for(int i = 0; i < this.radarItems.size(); i++)
            {
                RadarItem currentItem = this.radarItems.get(i);

                if(currentItem.getId()==assessmentItemId)
                {
                    currentItem.setRadarCategory(radarCategory);
                    currentItem.setRadarRing(radarRing);
                    currentItem.setConfidenceFactor(confidenceLevel);
                    currentItem.setDetails(assessmentDetails);
                    break;
                }
            }
        }
    }

    public void removeRadarItem(Long assessmentItemId)
    {
        if(this.isLocked==false)
        {
            if (this.radarItems != null)
            {
                for (int i = 0; i < this.radarItems.size(); i++)
                {
                    if (this.radarItems.get(i).getId() == assessmentItemId)
                    {
                        this.radarItems.remove(i);
                        break;
                    }
                }
            }
        }
    }
}