package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.stereotype.Component;

@Component
public class RadarServiceFactory
{
    private RadarUser targetDataOwner;
    private RadarUser currentUser;

    private FullHistoryRadarService fullHistoryRadarService;
    private MostRecentRadarService mostRecentRadarService;

    public RadarServiceFactory(FullHistoryRadarService fullHistoryRadarService, MostRecentRadarService mostRecentRadarService)
    {
        this.fullHistoryRadarService = fullHistoryRadarService;
        this.mostRecentRadarService = mostRecentRadarService;
    }

    public void setUserDetails(RadarUser currentUser, RadarUser targetDataOwner)
    {
        this.currentUser = currentUser;
        this.targetDataOwner = targetDataOwner;
    }

    public MostRecentRadarService getMostRecent() { return this.mostRecentRadarService;}

    public FullHistoryRadarService getFullHistory() { return this.fullHistoryRadarService;}

    public RadarServiceBase getRadarTypeServiceForSharing() {
        RadarServiceBase retVal = this.mostRecentRadarService;

        if (this.currentUser == null)
        {
            if (this.targetDataOwner != null)
            {
                if (targetDataOwner.canShareRadarTypes() == true)
                {
                    // public access, but the target owner can share their full history publicly.
                    retVal = this.fullHistoryRadarService;
                }
            }
        }
        else
        {
            if (targetDataOwner != null && this.currentUser.getId() == targetDataOwner.getId())
            {
                // the current user is the data owner, let them see all their data.
                retVal = this.fullHistoryRadarService;
            }
            else
            {
                if (this.targetDataOwner.canShareRadarTypes() == true)
                {
                    // logged in user is different from data owner, but the data owner canshare their full history
                    retVal = this.fullHistoryRadarService;
                }
            }
        }

        return retVal;
    }
}
