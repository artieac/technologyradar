package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/19/2016.
 */
public class RadarRing implements Serializable
{
    private Long id;

    private String name;

    /// I Hate having a display order come all the way from the database, TBD move this so it is just
    // a presentation layer concern somehow
    private Long displayOrder;

    private Date createDate;

    public RadarRing()
    {
        this.createDate = new Date();
    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public Date getCreateDate() { return createDate;}

    public void setCreateDate(Date createDate) { this.createDate = createDate;}

    public Long getDisplayOrder(){ return displayOrder;}

    public void setDisplayOrder(Long id){ this.displayOrder = id;}
}