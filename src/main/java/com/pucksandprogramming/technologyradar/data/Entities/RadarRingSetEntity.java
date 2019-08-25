package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RadarRingSets")
public class RadarRingSetEntity
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

    @OneToMany(mappedBy = "radarRingSet", cascade = CascadeType.ALL)
    private List<RadarRingEntity> radarRings;

    public RadarRingSetEntity()
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

    public List<RadarRingEntity> getRadarRings() { return this.radarRings;}
    public void setRadarRings(List<RadarRingEntity> value) { this.radarRings = value;}
}
