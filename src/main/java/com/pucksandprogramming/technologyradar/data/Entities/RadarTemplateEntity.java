package com.pucksandprogramming.technologyradar.data.Entities;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RadarTemplates")
public class RadarTemplateEntity
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

    @ManyToOne
    @JoinColumn(name = "RadarRingSetId", nullable=false)
    private RadarRingSetEntity radarRingSet;

    @ManyToOne
    @JoinColumn(name = "RadarCategorySetId", nullable=false)
    private RadarCategorySetEntity radarCategorySet;

    @Column(name="State", nullable=false)
    private Integer state;

    public RadarTemplateEntity()
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

    public RadarRingSetEntity getRadarRingSet() { return this.radarRingSet;}
    public void setRadarRingSet(RadarRingSetEntity value) { this.radarRingSet = value;}

    public RadarCategorySetEntity getRadarCategorySet() { return this.radarCategorySet;}
    public void setRadarCategorySet(RadarCategorySetEntity value) { this.radarCategorySet = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}
}
