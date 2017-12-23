package com.alwaysmoveforward.technologyradar.data.Entities;

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

    @Column(name = "QuadrantStart", nullable=false, length=512)
    private Integer quadrantStart;

    public RadarCategoryEntity()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}

    public void setName(String value) { this.name = value;}

    public String getColor() { return this.color;}

    public void setColor(String value) { this.color = value;}

    public Integer getQuadrantStart() { return this.quadrantStart;}

    public void setQuadrantStart(Integer value) { this.quadrantStart = value;}
}
