package com.alwaysmoveforward.technologyradar.data.dto;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessmentItems", schema = "dbo", catalog = "TechnologyRadar")
public class TechnologyAssessmentItemDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @JoinColumn(name = "technologyAssessmentId", referencedColumnName = "id")
    @ManyToOne(optional=false)
    private TechnologyAssessmentDTO technologyAssessment;

    @ManyToOne
    @JoinColumn(name = "TechnologyId", nullable=false)
    private TechnologyDTO technology;

    @Column(name = "Assessor", nullable = false, length = 255)
    private String assessor;

    @Column(name = "Details", nullable = false, length = 4096)
    private String details;

    @ManyToOne
    @JoinColumn(name = "RadarStateId", nullable=false)
    private RadarStateDTO radarState;

    @ManyToOne
    @JoinColumn(name = "RadarCategoryId", nullable=false)
    private RadarCategoryDTO radarCategory;

    public TechnologyAssessmentItemDTO()
    {

    }

    public Long getId(){ return id;}

    public void setId(Long value){ this.id = value;}

    public TechnologyAssessmentDTO getTechnologyAssessment() { return this.technologyAssessment;}

    public void setTechnologyAssessment(TechnologyAssessmentDTO value) { this.technologyAssessment = value;}

    public TechnologyDTO getTechnology() { return this.technology;}

    public void setTechnology(TechnologyDTO value) { this.technology = value;}

    public String getAssessor() { return this.assessor;}

    public void setAssessor(String value) { this.assessor = value;}

    public String getDetails() { return this.details;}

    public void setDetails(String value) { this.details = value;}

    public RadarStateDTO getRadarState() { return this.radarState;}

    public void setRadarState(RadarStateDTO value) { this.radarState = value;}

    public RadarCategoryDTO getRadarCategory() { return this.radarCategory;}

    public void setRadarCategory(RadarCategoryDTO value) { this.radarCategory = value;}
}
