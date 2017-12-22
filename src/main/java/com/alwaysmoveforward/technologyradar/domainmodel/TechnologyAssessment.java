package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
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

    private AssessmentTeam team;

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

    public AssessmentTeam getAssessmentTeam() { return this.team;}

    public void setAssessmentTeam(AssessmentTeam value) { this.team = value;}

}