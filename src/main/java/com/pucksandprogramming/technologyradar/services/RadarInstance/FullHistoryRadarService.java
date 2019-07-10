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
public class FullHistoryRadarService extends RadarServiceBase
{
    @Autowired
    public FullHistoryRadarService(RadarRepository radarRepository,
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
            retVal = this.radarRepository.findAllByRadarUserAndIsPublished(foundUser.getId(), publishedOnly);
        }

        return retVal;
    }

    public Radar findByUserAndRadarId(Long radarUserId, Long radarId, boolean publishedOnly)
    {
        Radar retVal = null;

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findByIdAndRadarUserIdAndPublishedOnly(foundUser.getId(), radarId, publishedOnly);
        }

        return retVal;
    }

    public List<Radar> findByUserAndType(Long radarUserId, String radarTypeId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findAllByUserTypeAndIsPublished(foundUser.getId(), radarTypeId, publishedOnly);
        }

        return retVal;
    }

    public List<Radar> findByUserTypeAndVersion(Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findAllByUserTypeVersionAndIsPublished(foundUser.getId(), radarTypeId, radarTypeVersion, publishedOnly);
        }

        return retVal;
    }

    public List<Radar> getAllNotOwnedByTechnologyId(Long technologyId, RadarUser currentUser)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null && currentUser != null && currentUser.canSeeHistory()==true)
        {
            retVal = this.radarRepository.findAllNotOwnedByTechnolgyIdAndRadarUserId(foundItem.getId(), currentUser.getId());
        }

        return retVal;
    }


}