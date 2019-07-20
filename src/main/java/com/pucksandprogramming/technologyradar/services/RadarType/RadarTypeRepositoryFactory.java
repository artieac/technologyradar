package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.PublicRadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeHistoryRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepositoryBase;
import com.pucksandprogramming.technologyradar.data.repositories.RecentRadarTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RadarTypeRepositoryFactory
{
    @Autowired
    private RadarTypeAccessManager radarTypeAccessManager;

    @Autowired
    private PublicRadarTypeRepository publicRepository;

    @Autowired
    private RecentRadarTypeRepository recentRadarTypeRepository;

    @Autowired
    private RadarTypeHistoryRepository radarTypeHistoryRepository;

    public RadarTypeRepositoryFactory()
    {
    }

    RecentRadarTypeRepository getMostRecentRepository() { return this.recentRadarTypeRepository;}

    public RadarTypeRepositoryBase getRadarTypeRepository(RadarUser targetDataOwner)
    {
        RadarTypeRepositoryBase retVal = this.publicRepository;

        RadarTypeAccessManager.AccessMode accessMode = this.radarTypeAccessManager.canViewRadarTypes(targetDataOwner);

        switch (accessMode)
        {
            case FullAccess:
                retVal = this.radarTypeHistoryRepository;
                break;
            case MostRecentAccess:
                retVal = this.recentRadarTypeRepository;
                break;
            default:
                retVal = this.publicRepository;
        }

        return retVal;
    }
}
