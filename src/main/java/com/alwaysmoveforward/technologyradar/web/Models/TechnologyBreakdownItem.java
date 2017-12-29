package com.alwaysmoveforward.technologyradar.web.Models;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarRing;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;

import java.util.Date;

/**
 * Created by acorrea on 12/28/2017.
 */
public class TechnologyBreakdownItem {
    Long assessmentId;
    Date assessmentDate;
    String assessmentName;
    RadarUser assessmentUser;
    RadarRing assessmentRing;
    String assessmentDetails;

    public Long getAssessmentId() { return this.assessmentId;}
    public void setAssessmentId(Long value) { this.assessmentId = value;}

    public Date getAssessmentDate() { return this.assessmentDate;}
    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public String getAssessmentName() { return this.assessmentName;}
    public void setAssessmentName(String value) { this.assessmentName = value;}

    public RadarUser getAssessmentUser() { return this.assessmentUser;}
    public void setAssessmentUser(RadarUser value) { this.assessmentUser = value;}

    public RadarRing getAssessmentRing() { return this.assessmentRing;}
    public void setAssessmentRing(RadarRing value) { this.assessmentRing = value;}

    public String getAssessmentDetails() { return this.assessmentDetails;}
    public void setAssessmentDetails(String value) { this.assessmentDetails = value;}
}
