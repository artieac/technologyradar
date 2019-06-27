package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RadarTypeServiceFactory
{
    RadarUser currentUser;

    private HistoryRadarTypeService historyRadarTypeService;
    private MostRecentRadarTypesService mostRecentRadarTypesService;

    @Autowired
    public RadarTypeServiceFactory(HistoryRadarTypeService historyRadarTypeService, MostRecentRadarTypesService mostRecentRadarTypesService)
    {
        this.historyRadarTypeService = historyRadarTypeService;
        this.mostRecentRadarTypesService = mostRecentRadarTypesService;
    }

    public void setCurrentUser(RadarUser value) { this.currentUser = value;}

    public MostRecentRadarTypesService getMostRecent() { return this.mostRecentRadarTypesService;}

    public IRadarTypeService getRadarTypeService()
    {
        IRadarTypeService retVal = this.mostRecentRadarTypesService;

        if(this.currentUser.canSeeHistory()==true)
        {
            retVal = this.historyRadarTypeService;
        }

        return retVal;
    }
}
