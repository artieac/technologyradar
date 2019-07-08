package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "RadarRings")
@org.hibernate.annotations.NamedNativeQueries
(
        {
                @org.hibernate.annotations.NamedNativeQuery(name = "checkIfHasItems", query = "SELECT RadarRingId, MAX(Id) FROM TechnologyAssessmentItems WHERE RadarRingId IN :radarRingIdList GROUP BY RadarRingId", resultClass = RadarTypeEntity.class),
        }
)



public class RadarRingEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    /// I Hate having a display order come all the way from the database, TBD move this so it is just
    // a presentation layer concern somehow
    @Column(name="DisplayOrder", nullable=false)
    private Long displayOrder;

    @ManyToOne(optional=false)
    @JoinColumns
    ({
        @JoinColumn(name="RadarTypeId", referencedColumnName="Id"),
        @JoinColumn(name="RadarTypeVersion", referencedColumnName="Version")
    })
    private RadarTypeEntity radarType;

    public RadarRingEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public Long getDisplayOrder(){ return this.displayOrder;}
    public void setDisplayOrder(Long value){ this.displayOrder = value;}

    public RadarTypeEntity getRadarType() { return this.radarType;}
    public void setRadarType(RadarTypeEntity value) { this.radarType = value;}
}
