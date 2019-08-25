package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;

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

    @JoinColumn(name = "radarCategorySetId", referencedColumnName = "id")
    @ManyToOne(optional=false)
    private RadarCategorySetEntity radarCategorySet;

    public RadarCategoryEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getColor() { return this.color;}
    public void setColor(String value) { this.color = value;}

    public RadarCategorySetEntity getRadarCategorySet() { return this.radarCategorySet;}
    public void setRadarCategorySet(RadarCategorySetEntity value) { this.radarCategorySet = value;}
}
