package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.Entities.RadarInstanceEntity;
import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostRecentRadarInstanceService extends RadarInstanceService
{
    @Autowired
    public MostRecentRadarInstanceService(RadarInstanceRepository radarInstanceRepository,
                                          TechnologyRepository technologyRepository,
                                          RadarRingRepository radarRingRepository,
                                          RadarCategoryRepository radarCategoryRepository,
                                          RadarUserRepository radarUserRepository,
                                          RadarTypeRepository radarTypeRepository)
    {
        super(radarInstanceRepository, technologyRepository, radarRingRepository, radarCategoryRepository, radarUserRepository, radarTypeRepository);
    }

    public List<Radar> findByRadarUserId(Long radarUserId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarInstanceRepository.findMostRecentByUserIdAndPublishedOnly(foundUser.getId(), publishedOnly));
        }

        return retVal;
    }

    public Radar findByUserAndRadarId(Long radarUserId, Long radarId, boolean publishedOnly)
    {
        Radar retVal = null;

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarInstanceRepository.findMostRecentByUserRadarIdAndPublishedOnly(foundUser.getId(), radarId, publishedOnly);

            if(retVal == null)
            {
                Radar unpublishedRadar = this.radarInstanceRepository.findMostRecentByUserRadarIdAndPublishedOnly(foundUser.getId(), radarId, false);

                if(unpublishedRadar!=null)
                {
                    retVal = this.radarInstanceRepository.findMostRecentByUserIdRadarTypeAndPublished(foundUser.getId(), unpublishedRadar.getRadarType().getId(), true);
                }
            }
        }

        return retVal;
    }

    public List<Radar> findByUserAndType(Long radarUserId, Long radarTypeId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarInstanceRepository.findAMostRecentByUserTypeAndIsPublished(foundUser.getId(), radarTypeId, publishedOnly));
        }

        return retVal;
    }

    public List<Radar> findByUserTypeAndVersion(Long radarUserId, Long radarTypeId, Long radarTypeVersion, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarInstanceRepository.findAMostRecentByUserTypeVersionAndIsPublished(foundUser.getId(), radarTypeId, radarTypeVersion, publishedOnly));
        }

        return retVal;
    }

    public List<Radar> getAllNotOwnedByTechnologyId(Long technologyId, RadarUser currentUser)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            if (currentUser != null)
            {
                retVal = this.radarInstanceRepository.findMostNotOwnedByTechnolgyIdAndRadarUserId(foundItem.getId(), currentUser.getId());
            }
            else
            {
                retVal = this.radarInstanceRepository.findMostRecentByTechnolgyId(foundItem.getId());
            }
        }

        return retVal;
    }
}
