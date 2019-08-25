package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RadarCategorySets")
public class RadarCategorySetEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable=false, length=50)
    private String name;

    @Column(name = "Description", nullable=false, length=1024)
    private String description;

    @ManyToOne
    @JoinColumn(name = "RadarUserId", nullable=false)
    private RadarUserEntity radarUser;

    @OneToMany(mappedBy = "radarCategorySet", cascade = CascadeType.ALL)
    private List<RadarCategoryEntity> radarCategories;

    public RadarCategorySetEntity()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public RadarUserEntity getRadarUser(){ return this.radarUser;}
    public void setRadarUser(RadarUserEntity value){ this.radarUser = value;}

    public List<RadarCategoryEntity> getRadarCategories() { return this.radarCategories;}
    public void setRadarCategories(List<RadarCategoryEntity> value) { this.radarCategories = value;}
}
