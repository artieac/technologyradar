package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RadarTypes")
public class RadarTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=512)
    private String name;

    @OneToMany(mappedBy = "radarType")
    private List<RadarRingEntity> radarRings;

    @OneToMany(mappedBy = "radarType")
    private List<RadarCategoryEntity> radarCategories;

    @ManyToOne
    @JoinColumn(name = "RadarUserId", nullable=false)
    private RadarUserEntity radarUser;

    public RadarTypeEntity()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}

    public void setName(String value) { this.name = value;}

    public RadarUserEntity getRadarUser(){ return this.radarUser;}

    public void setRadarUser(RadarUserEntity value){ this.radarUser = value;}

    public List<RadarRingEntity> getRadarRings() { return this.radarRings;}

    public void setRadarRings(List<RadarRingEntity> value) { this.radarRings = value;}

    public List<RadarCategoryEntity> getRadarCategories() { return this.radarCategories;}

    public void setRadarCategories(List<RadarCategoryEntity> value) { this.radarCategories = value;}

}

