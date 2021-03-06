package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "TechnologyAssessmentItems")
@org.hibernate.annotations.NamedNativeQueries
(
        {
                @org.hibernate.annotations.NamedNativeQuery(name = "getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId", query = "Select * from TechnologyAssessmentItems where TechnologyId = :technologyId AND TechnologyAssessmentId IN (\n" +
                        "select MAX(ta.Id) FROM TechnologyAssessments ta, TechnologyAssessmentItems tai where \n" +
                        "\tta.RadarUserId = :radarUserId\n" +
                        "    AND ta.Id < :previousRadarInstanceId\n" +
                        "\tAND tai.TechnologyAssessmentId = ta.Id\n" +
                        "    AND tai.TechnologyId = :technologyId) ORDER BY Id;", resultClass = RadarItemEntity.class),
        }
)
public class RadarItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @JoinColumn(name = "technologyAssessmentId", referencedColumnName = "id")
    @ManyToOne(optional=false)
    private RadarEntity radarInstance;

    @ManyToOne
    @JoinColumn(name = "TechnologyId", nullable=false)
    private TechnologyEntity technology;

    @Column(name = "Details", nullable = false, length = 4096)
    private String details;

    @ManyToOne
    @JoinColumn(name = "RadarRingId", nullable=false)
    private RadarRingEntity radarRing;

    @ManyToOne
    @JoinColumn(name = "RadarCategoryId", nullable=false)
    private RadarCategoryEntity radarCategory;

    @Column(name = "ConfidenceFactor", nullable = false)
    private Integer confidenceFactor;

    @Column(name = "State")
    private Integer state;

    public RadarItemEntity() {

    }

    public Long getId(){ return id;}
    public void setId(Long value){ this.id = value;}

    public RadarEntity getRadarInstance() { return this.radarInstance;}
    public void setRadarInstance(RadarEntity value) { this.radarInstance = value;}

    public TechnologyEntity getTechnology() { return this.technology;}
    public void setTechnology(TechnologyEntity value) { this.technology = value;}

    public String getDetails() { return this.details;}
    public void setDetails(String value) { this.details = value;}

    public RadarRingEntity getRadarRing() { return this.radarRing;}
    public void setRadarRing(RadarRingEntity value) { this.radarRing = value;}

    public RadarCategoryEntity getRadarCategory() { return this.radarCategory;}
    public void setRadarCategory(RadarCategoryEntity value) { this.radarCategory = value;}

    public Integer getConfidenceFactor() { return this.confidenceFactor;}
    public void setConfidenceFactor(Integer value) { this.confidenceFactor = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}
}
