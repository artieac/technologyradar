package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/18/2016.
 */
public class Technology implements Serializable
{
    private Long id;

    private String name;

    private String description;

    private String url;

    private Date createDate;

    private String creator;

    private RadarCategory radarCategory;

    public Technology()
    {
        this.createDate = new Date();
    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public Date getCreateDate() { return createDate;}

    public void setCreateDate(Date createDate) { this.createDate = createDate;}

    public String getCreator() { return creator;}

    public void setCreator(String creator) { this.creator = creator;}

    public  String getDescription() { return this.description;}

    public void setDescription(String value) { this.description = value;}

    public  String getUrl() { return this.url;}

    public void setUrl(String value) { this.url = value;}

    public RadarCategory getRadarCategory() { return this.radarCategory;}

    public void setRadarCategory(RadarCategory value) { this.radarCategory = value;}
}
