package com.pucksandprogramming.technologyradar.web.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by acorrea on 10/20/2016.
 */
public class PolarCoords
{
    private Integer radial;
    private Integer angular;

    public PolarCoords(Integer radial, Integer angular)
    {
        this.radial = radial;
        this.angular = angular;
    }

    @JsonProperty(value="r")
    public Integer getRadial() { return this.radial;}
    public void setRadial(Integer value) { this.radial = value;}

    @JsonProperty(value="t")
    public Integer getAngular() { return this.angular;}
    public void setAngular(Integer value) { this.angular = value;}
}
