package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.PublicRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRepositoryBase;
import com.pucksandprogramming.technologyradar.data.repositories.TeamRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RadarRepositoryFactory
{
    private RadarAccessManager radarAccessManager;
    private PublicRadarRepository publicRadarRepository;
    private FullRadarRepository fullRadarRepository;
    private TeamRepository teamRepository;

    @Autowired
    public RadarRepositoryFactory(RadarAccessManager radarAccessManager, PublicRadarRepository publicRadarRepository, FullRadarRepository fullRadarRepository, TeamRepository teampRepository)
    {
        this.radarAccessManager = radarAccessManager;
        this.publicRadarRepository = publicRadarRepository;
        this.fullRadarRepository = fullRadarRepository;
        this.teamRepository = teampRepository;
    }

    public RadarRepositoryBase getRadarRepository(RadarUser targetDataOwner)
    {
        RadarRepositoryBase retVal = this.publicRadarRepository;

        RadarAccessManager.ViewAccessMode viewMode = this.radarAccessManager.canViewUserRadars(targetDataOwner);

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

    public RadarRepositoryBase getRadarRepositoryForRadarInstance(RadarUser targetDataOwner, Long radarId)
    {
        RadarRepositoryBase retVal = this.publicRadarRepository;

        RadarAccessManager.ViewAccessMode viewMode = this.radarAccessManager.canViewRadar(targetDataOwner, radarId);

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
