package com.pucksandprogramming.technologyradar.data.Entities;

import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "AssociatedRadarTemplates")
public class AssociatedRadarTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "RadarUserId", nullable=false)
    private Long radarUserId;

    @Column(name = "RadarTemplateId", nullable = false)
    private Long radarTemplateId;

    public AssociatedRadarTemplateEntity() {

    }

    public Long getId() { return this.id;}
    public void setId(Long value) { this.id = value;}

    public Long getRadarUserId() { return this.radarUserId;}
    public void setRadarUserId(Long value) { this.radarUserId = value;}

    public Long getRadarTemplateId() { return this.radarTemplateId;}
    public void setRadarTemplateId(Long value) { this.radarTemplateId = value;}
}
