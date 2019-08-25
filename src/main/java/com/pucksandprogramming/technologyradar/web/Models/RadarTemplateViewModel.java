package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

public class RadarTemplateViewModel
{
    private Long id;
    private String name;
    private String description;
    private UserViewModel radarUser;
    private RadarRingSetViewModel radarRingSet;
    private RadarCategorySetViewModel radarCategorySet;
    private Integer state;

    public RadarTemplateViewModel()
    {

    }

    public RadarTemplateViewModel(RadarTemplate source)
    {
        this.id = source.getId();
        this.name = source.getName();
        this.description = source.getDescription();
        this.radarUser = new UserViewModel(source.getRadarUser());
        this.radarRingSet = new RadarRingSetViewModel(source.getRadarRingSet());
        this.radarCategorySet = new RadarCategorySetViewModel(source.getRadarCategorySet());
        this.state = state;
    }

    public RadarTemplate convertToRadarTemplate()
    {
        RadarTemplate retVal = new RadarTemplate();

        retVal.setId(this.getId());
        retVal.setName(this.getName());
        retVal.setDescription(this.getDescription());
        retVal.setRadarRingSet(this.getRadarRingSet().ConvertToRadarRingSet());
        retVal.setRadarCategorySet(this.getRadarCategorySet().ConvertToRadarCategorySet());
        return retVal;
    }

    public Long getId(){ return this.id;}
    public void setId(Long value){ this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getDescription() { return this.description;}
    public void setDescription(String value) { this.description = value;}

    public UserViewModel getRadarUser(){ return this.radarUser;}
    public void setRadarUser(UserViewModel value){ this.radarUser = value;}

    public RadarRingSetViewModel getRadarRingSet() { return this.radarRingSet;}
    public void setRadarRingSet(RadarRingSetViewModel value) { this.radarRingSet = value;}

    public RadarCategorySetViewModel getRadarCategorySet() { return this.radarCategorySet;}
    public void setRadarCategorySet(RadarCategorySetViewModel value) { this.radarCategorySet = value;}

    public Integer getState() { return this.state;}
    public void setState(Integer value) { this.state = value;}
}
