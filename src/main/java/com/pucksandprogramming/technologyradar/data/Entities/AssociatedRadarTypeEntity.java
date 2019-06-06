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

//    @ManyToOne
//    @JoinColumn(name = "RadarUserId", nullable=false)
//    private RadarUserEntity radarUser;

//    @Column(name = "RadarTypeId", nullable = false)
//    private List<RadarTypeEntity> radarTypes;

    public AssociatedRadarTypeEntity()
    {

    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

//    public RadarUserEntity getRadarUser() { return this.radarUser;}
//    public void setRadarUser(RadarUserEntity value) { this.radarUser = value;}

//    public List<RadarTypeEntity> getRadarType() { return this.radarTypes;}
//    public void setRadarType(List<RadarTypeEntity> value) { this.radarTypes = value;}
}
