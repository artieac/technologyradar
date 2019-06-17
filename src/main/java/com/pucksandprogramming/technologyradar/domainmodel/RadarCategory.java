package com.pucksandprogramming.technologyradar.domainmodel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by acorrea on 10/19/2016.
 */
public class RadarCategory implements Serializable
{
    private Long id;
    private String name;
    private String color;

    public RadarCategory()
    {
    }

    public Long getId(){ return id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return name;}
    public void setName(String value) { this.name = value;}

    public String getColor() { return color;}
    public void setColor(String value) { this.color = value;}
}
