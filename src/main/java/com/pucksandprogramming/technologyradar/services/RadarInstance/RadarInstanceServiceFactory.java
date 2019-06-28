package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.stereotype.Component;

@Component
public class RadarInstanceServiceFactory
{
    private RadarUser targetDataOwner;
    private RadarUser currentUser;

    private FullHistoryRadarInstanceService fullHistoryRadarInstanceService;
    private MostRecentRadarInstanceService mostRecentRadarInstanceService;

    public RadarInstanceServiceFactory(FullHistoryRadarInstanceService fullHistoryRadarInstanceService, MostRecentRadarInstanceService mostRecentRadarInstanceService)
    {
        this.fullHistoryRadarInstanceService = fullHistoryRadarInstanceService;
        this.mostRecentRadarInstanceService = mostRecentRadarInstanceService;
    }

    public void setUserDetails(RadarUser currentUser, RadarUser targetDataOwner)
    {
        this.currentUser = currentUser;
        this.targetDataOwner = targetDataOwner;
    }

    public MostRecentRadarInstanceService getMostRecent() { return this.mostRecentRadarInstanceService;}

    public FullHistoryRadarInstanceService getFullHistory() { return this.fullHistoryRadarInstanceService;}

    public RadarInstanceService getRadarTypeServiceForSharing() {
        RadarInstanceService retVal = this.mostRecentRadarInstanceService;

        if (this.currentUser == null)
        {
            if (this.targetDataOwner != null)
            {
                if (targetDataOwner.canShareHistory() == true)
                {
                    // public access, but the target owner can share their full history publicly.
                    retVal = this.fullHistoryRadarInstanceService;
                }
            }
        }
        else
        {
            if (targetDataOwner != null && this.currentUser.getId() == targetDataOwner.getId())
            {
                // the current user is the data owner, let them see all their data.
                retVal = this.fullHistoryRadarInstanceService;
            }
            else
            {
                if (this.targetDataOwner.canShareHistory() == true)
                {
                    // logged in user is different from data owner, but the data owner canshare their full history
                    retVal = this.fullHistoryRadarInstanceService;
                }
            }
        }

        return retVal;
    }
}
