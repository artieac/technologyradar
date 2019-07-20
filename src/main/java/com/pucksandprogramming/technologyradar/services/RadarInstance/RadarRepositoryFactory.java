package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.PublicRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRepositoryBase;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RadarRepositoryFactory
{
    private RadarAccessManager radarAccessManager;
    private PublicRadarRepository publicRadarRepository;
    private FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarRepositoryFactory(RadarAccessManager radarAccessManager, PublicRadarRepository publicRadarRepository, FullRadarRepository fullRadarRepository)
    {
        this.radarAccessManager = radarAccessManager;
        this.publicRadarRepository = publicRadarRepository;
        this.fullRadarRepository = fullRadarRepository;
    }

    public RadarRepositoryBase getRadarRepository(RadarUser targetDataOwner)
    {
        RadarRepositoryBase retVal = this.publicRadarRepository;

        RadarAccessManager.ViewAccessMode viewMode = this.radarAccessManager.canViewHistory(targetDataOwner);

        switch(viewMode)
        {
            case FullAccess:
                retVal = this.fullRadarRepository;
                break;
            default:
                retVal = this.publicRadarRepository;
        }

        return retVal;
    }
}