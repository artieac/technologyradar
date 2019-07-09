package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostRecentRadarService extends RadarServiceBase
{
    @Autowired
    public MostRecentRadarService(RadarRepository radarRepository,
                                  TechnologyRepository technologyRepository,
                                  RadarRingRepository radarRingRepository,
                                  RadarCategoryRepository radarCategoryRepository,
                                  RadarUserRepository radarUserRepository,
                                  RadarTypeRepository radarTypeRepository)
    {
        super(radarRepository, technologyRepository, radarRingRepository, radarCategoryRepository, radarUserRepository, radarTypeRepository);
    }

    public List<Radar> findByRadarUserId(Long radarUserId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarRepository.findMostRecentByUserIdAndPublishedOnly(foundUser.getId(), publishedOnly));
        }

        return retVal;
    }

    public Radar findByUserAndRadarId(Long radarUserId, Long radarId, boolean publishedOnly)
    {
        Radar retVal = null;

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findMostRecentByUserRadarIdAndPublishedOnly(foundUser.getId(), radarId, publishedOnly);

            if(retVal == null)
            {
                Radar unpublishedRadar = this.radarRepository.findMostRecentByUserRadarIdAndPublishedOnly(foundUser.getId(), radarId, false);

                if(unpublishedRadar!=null)
                {
                    retVal = this.radarRepository.findMostRecentByUserIdRadarTypeAndPublished(foundUser.getId(), unpublishedRadar.getRadarType().getId(), true);
                }
            }
        }

        return retVal;
    }

    public List<Radar> findByUserAndType(Long radarUserId, String radarTypeId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarRepository.findAMostRecentByUserTypeAndIsPublished(foundUser.getId(), radarTypeId, publishedOnly));
        }

        return retVal;
    }

    public List<Radar> findByUserTypeAndVersion(Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal.add(this.radarRepository.findAMostRecentByUserTypeVersionAndIsPublished(foundUser.getId(), radarTypeId, radarTypeVersion, publishedOnly));
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
                retVal = this.radarRepository.findMostNotOwnedByTechnolgyIdAndRadarUserId(foundItem.getId(), currentUser.getId());
            }
            else
            {
                retVal = this.radarRepository.findMostRecentByTechnolgyId(foundItem.getId());
            }
        }

        return retVal;
    }
}
