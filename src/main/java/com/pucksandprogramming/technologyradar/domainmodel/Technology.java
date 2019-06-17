package com.pucksandprogramming.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/18/2016.
 */
public class Technology implements Serializable
{
    private Long id;
    private String name;
    private String url;
    private Date createDate;
    private String creator;

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

    public  String getUrl() { return this.url;}
    public void setUrl(String value) { this.url = value;}
}
