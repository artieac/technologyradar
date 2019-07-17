package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RadarTypeServiceFactory
{
    RadarUser targetDataOwner;
    RadarUser currentUser;

    RadarTypeAccessManager radarTypeAccessManager;

    private HistoryRadarTypeService historyRadarTypeService;
    private RadarTypeService mostRecentRadarTypesService;

    @Autowired
    public RadarTypeServiceFactory(RadarTypeAccessManager radarTypeAccessManager, HistoryRadarTypeService historyRadarTypeService, RadarTypeService mostRecentRadarTypesService)
    {
        this.radarTypeAccessManager = radarTypeAccessManager;
        this.historyRadarTypeService = historyRadarTypeService;
        this.mostRecentRadarTypesService = mostRecentRadarTypesService;
    }

    public boolean canShareRadarTypes(RadarUser dataOwner)
    {
        boolean retVal = false;

        if(dataOwner.getId() == this.radarTypeAccessManager.getAuthenticatedUser().getUserId())
        {
            retVal = true;
        }
        else
        {
            if(this.radarTypeAccessManager.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))
            {
                retVal = true;
            }
            else if(this.radarTypeAccessManager.getAuthenticatedUser().hasPrivilege(UserRights.CanViewHistory))
            {
                retVal = true;
            }
        }

        return retVal;
    }

    public RadarTypeService getMostRecent() { return this.mostRecentRadarTypesService;}

    public RadarTypeService getRadarTypeService(RadarUser targetDataOwner)
    {
        RadarTypeService retVal = this.mostRecentRadarTypesService;

        if(this.radarTypeAccessManager.canViewAllRadarTypes(targetDataOwner))
        {
            retVal = this.historyRadarTypeService;
        }

        return retVal;
    }
}
