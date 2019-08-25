package com.pucksandprogramming.technologyradar.domainmodel;

import java.util.List;

public class RadarTemplate
{
    public static final Integer State_Active = 1;
    public static final Integer State_InActive = 0;

    private Long id;
    private String name;
    private String description;
    private RadarUser radarUser;
    private RadarRingSet radarRingSet;
    private RadarCategorySet radarCategorySet;
    private Integer state;

    public RadarTemplate()
    {

    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public RadarUser getRadarUser(){ return this.radarUser;}
    public void setRadarUser(RadarUser value){ this.radarUser = value;}

    public RadarRingSet getRadarRingSet() { return this.radarRingSet;}
    public void setRadarRingSet(RadarRingSet value) { this.radarRingSet = value;}

    public RadarCategorySet getRadarCategorySet() { return this.radarCategorySet;}
    public void setRadarCategorySet(RadarCategorySet value) { this.radarCategorySet = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}
}
