package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RadarTypes")
@org.hibernate.annotations.NamedNativeQueries
(
    {
        @org.hibernate.annotations.NamedNativeQuery(name = "findAllAssociated", query = "select * from RadarTypes rt where rt.Id IN (SELECT art.RadarTypeId FROM AssociatedRadarTypes art WHERE art.RadarUserId = :radarUserId)", resultClass = RadarTypeEntity.class),         @org.hibernate.annotations.NamedNativeQuery(name = "findAllAssociatedRadarTypes", query = "select * from RadarTypes rt where t.Id IN (SELECT art.RadarTypeId FROM AssociatedRadarTypes art WHERE art.RadarUserId = :radarUserId)", resultClass = RadarTypeEntity.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "findOthersRadarTypes", query = "select * from RadarTypes rt where rt.IsPublished = true AND rt.Id IN (SELECT art.RadarTypeId FROM AssociatedRadarTypes art WHERE art.RadarUserId <> :radarUserId)", resultClass = RadarTypeEntity.class),
    }
)
public class RadarTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @OneToMany(mappedBy = "radarType", cascade = CascadeType.ALL)
    private List<RadarRingEntity> radarRings;

    @OneToMany(mappedBy = "radarType", cascade = CascadeType.ALL)
    private List<RadarCategoryEntity> radarCategories;

    @ManyToOne
    @JoinColumn(name = "RadarUserId", nullable=false)
    private RadarUserEntity creator;

    @Column(name = "IsPublished", nullable=false)
    private boolean isPublished;

    public RadarTypeEntity()
    {
        this.setId(-1L);
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public RadarUserEntity getCreator(){ return this.creator;}
    public void setCreator(RadarUserEntity value){ this.creator = value;}

    public List<RadarRingEntity> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarRingEntity> value) { this.radarRings = value;}

    public List<RadarCategoryEntity> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarCategoryEntity> value) { this.radarCategories = value;}

    public boolean getIsPublished() { return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

}

