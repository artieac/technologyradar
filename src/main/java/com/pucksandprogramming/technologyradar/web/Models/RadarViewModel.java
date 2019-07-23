package com.pucksandprogramming.technologyradar.web.Models;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RadarViewModel
{
    private Long id;
    private String name;
    private Date assessmentDate;
    private String creator;
    private UserViewModel radarUser;
    private List<RadarItem> radarItems;
    private boolean isPublished;
    private boolean isLocked;
    private RadarTypeViewModel radarType;

    public RadarViewModel()
    {

    }

    public RadarViewModel(Radar source)
    {
        this.initialize(source);
    }

    public void initialize(Radar source)
    {
        this.id = source.getId();
        this.name = source.getName();
        this.assessmentDate = source.getAssessmentDate();
        this.creator = source.getCreator();
        this.radarUser = new UserViewModel(source.getRadarUser());
        this.radarItems = source.getRadarItems();
        this.isPublished = source.getIsPublished();
        this.isLocked = source.getIsLocked();
        this.radarType = new RadarTypeViewModel(source.getRadarType());
    }

    public Long getId(){ return id;}
    public void setId(Long id){ this.id = id;}

    public String getName() { return name;}
    public void setName(String value) { this.name = value;}

    public Date getAssessmentDate() { return assessmentDate;}
    public void setAssessmentDate(Date value) { this.assessmentDate = value;}

    public String getFormattedAssessmentDate()
    {
        String pattern = "MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(this.assessmentDate);
    }

    public String getCreator() { return creator;}
    public void setCreator(String value) { this.creator = value;}

    public List<RadarItem> getRadarItems() { return radarItems;}
    public void setRadarItems(List<RadarItem> value) { this.radarItems = value;}

    public UserViewModel getRadarUser() { return this.radarUser;}
    public void setRadarUser(UserViewModel value) { this.radarUser = value;}

    public boolean getIsPublished(){ return this.isPublished;}
    public void setIsPublished(boolean value){ this.isPublished = value;}

    public boolean getIsLocked(){ return this.isLocked;}
    public void setIsLocked(boolean value){ this.isLocked = value;}

    public RadarTypeViewModel getRadarType() { return this.radarType;}
    public void setRadarType(RadarTypeViewModel value) { this.radarType = value;}

}
