package com.pucksandprogramming.technologyradar.data.Entities;

import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "AssociatedRadarTypes")
public class AssociatedRadarTypeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "RadarUserId", nullable=false)
    private Long radarUserId;

    @Column(name = "RadarTypeId", nullable = false)
    private Long radarTypeId;

    public AssociatedRadarTypeEntity()
    {

    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public Long getRadarUserId() { return this.radarUserId;}
    public void setRadarUserId(Long value) { this.radarUserId = value;}

    public Long getRadarTypeId() { return this.radarTypeId;}
    public void setRadarTypeId(Long value) { this.radarTypeId = value;}
}
