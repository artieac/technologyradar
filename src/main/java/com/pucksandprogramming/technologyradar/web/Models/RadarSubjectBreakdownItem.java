package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

import java.util.Date;

/**
 * Created by acorrea on 12/28/2017.
 */
public class RadarSubjectBreakdownItem {
    Long assessmentId;
    Date assessmentDate;
    String assessmentName;
    UserViewModel assessmentUser;
    RadarRing assessmentRing;
    RadarCategory assessmentCategory;
    String assessmentDetails;

    public Long getAssessmentId() { return this.assessmentId;}
    public void setAssessmentId(Long value) { this.assessmentId = value;}

    public Date getAssessmentDate() { return this.assessmentDate;}
    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public String getAssessmentName() { return this.assessmentName;}
    public void setAssessmentName(String value) { this.assessmentName = value;}

    public UserViewModel getAssessmentUser() { return this.assessmentUser;}
    public void setAssessmentUser(UserViewModel value) { this.assessmentUser = value;}

    public RadarRing getAssessmentRing() { return this.assessmentRing;}
    public void setAssessmentRing(RadarRing value) { this.assessmentRing = value;}

    public RadarCategory getAssessmentCategory() { return this.assessmentCategory;}
    public void setAssessmentCategory(RadarCategory value) { this.assessmentCategory = value;}

    public String getAssessmentDetails() { return this.assessmentDetails;}
    public void setAssessmentDetails(String value) { this.assessmentDetails = value;}
}
