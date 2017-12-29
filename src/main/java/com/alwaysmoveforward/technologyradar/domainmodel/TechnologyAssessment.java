package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
public class TechnologyAssessment implements Serializable
{
    private Long id;

    private String name;

    private Date assessmentDate;

    private String creator;

    private RadarUser radarUser;

    private List<TechnologyAssessmentItem> technologyAssessments;

    public TechnologyAssessment()
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

    public List<TechnologyAssessmentItem> getTechnologyAssessmentItems() { return technologyAssessments;}

    public void setTechnologyAssessmentItems(List<TechnologyAssessmentItem> value) { this.technologyAssessments = value;}

    public RadarUser getRadarUser() { return this.radarUser;}

    public void setRadarUser(RadarUser value) { this.radarUser = value;}

    public void addAssessmentItem(TechnologyAssessmentItem newAssessmentItem)
    {
        if(this.technologyAssessments == null)
        {
            this.technologyAssessments = new ArrayList<TechnologyAssessmentItem>();
        }

        this.technologyAssessments.add(newAssessmentItem);
    }

    public void updateAssessmentItem(Long assessmentItemId, RadarRing radarRing, Integer confidenceLevel, String assessmentDetails)
    {
        for(int i = 0; i < this.getTechnologyAssessmentItems().size(); i++)
        {
            TechnologyAssessmentItem currentItem = this.getTechnologyAssessmentItems().get(i);

            if(currentItem.getId()==assessmentItemId)
            {
                currentItem.setRadarRing(radarRing);
                currentItem.setConfidenceFactor(confidenceLevel);
                currentItem.setDetails(assessmentDetails);
                break;
            }
        }
    }

    public void removeAssessmentItem(Long assessmentItemId){

        if(this.technologyAssessments != null)
        {
            for(int i = 0; i < this.technologyAssessments.size(); i++)
            {
                if(this.technologyAssessments.get(i).getId() == assessmentItemId){
                    this.technologyAssessments.remove(i);
                    break;
                }
            }
        }
    }
}