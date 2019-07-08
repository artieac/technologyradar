package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 10/19/2016.
 */
@Entity
@Table(name = "RadarCategories")
public class RadarCategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @Column(name = "Color", nullable=false, length=512)
    private String color;

    @ManyToOne(optional=false)
    @JoinColumns
    ({
        @JoinColumn(name="RadarTypeId", referencedColumnName="Id"),
        @JoinColumn(name="RadarTypeVersion", referencedColumnName="Version")
    })
    private RadarTypeEntity radarType;

    public RadarCategoryEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getColor() { return this.color;}
    public void setColor(String value) { this.color = value;}

    public RadarTypeEntity getRadarType() { return this.radarType;}
    public void setRadarType(RadarTypeEntity value) { this.radarType = value;}

}
