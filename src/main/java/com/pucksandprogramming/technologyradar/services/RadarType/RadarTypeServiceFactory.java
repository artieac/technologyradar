package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RadarTypeServiceFactory
{
    RadarUser targetDataOwner;
    RadarUser currentUser;

    private HistoryRadarTypeService historyRadarTypeService;
    private MostRecentRadarTypesService mostRecentRadarTypesService;

    @Autowired
    public RadarTypeServiceFactory(HistoryRadarTypeService historyRadarTypeService, MostRecentRadarTypesService mostRecentRadarTypesService)
    {
        this.historyRadarTypeService = historyRadarTypeService;
        this.mostRecentRadarTypesService = mostRecentRadarTypesService;
    }

    public void setUser(RadarUser currentUser, RadarUser targetDataOwner)
    {
        this.currentUser = currentUser;
        this.targetDataOwner = targetDataOwner;
    }

    public MostRecentRadarTypesService getMostRecent() { return this.mostRecentRadarTypesService;}

    public RadarTypeServiceBase getRadarTypeServiceForViewing()
    {
        RadarTypeServiceBase retVal = this.mostRecentRadarTypesService;

        if(currentUser != null)
        {
            if(this.currentUser.canSeeHistory()==true)
            {
                retVal = this.historyRadarTypeService;
            }
        }

        return retVal;
    }

    public RadarTypeServiceBase getRadarTypeServiceForSharing()
    {
        RadarTypeServiceBase retVal = this.mostRecentRadarTypesService;

        if(this.targetDataOwner != null)
        {
            if (this.targetDataOwner.canShareRadarTypes() == true)
            {
                retVal = this.historyRadarTypeService;
            }
        }

        return retVal;
    }

}
