package com.alwaysmoveforward.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/19/2016.
 */
public class AssessmentTeam implements Serializable
{
    private Long id;

    private String name;

    private Date createDate;

    public AssessmentTeam()
    {
        this.createDate = new Date();
    }

    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public Date getCreateDate() { return createDate;}

    public void setCreateDate(Date createDate) { this.createDate = createDate;}
}

