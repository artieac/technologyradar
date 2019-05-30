package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
@Component
public class RadarService
{
    private TechnologyRepository technologyRepository;
    private RadarRepository radarRepository;
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;
    private RadarUserRepository radarUserRepository;
    private RadarTypeRepository radarTypeRepository;

    @Autowired
    public RadarService(RadarRepository radarRepository,
                        TechnologyRepository technologyRepository,
                        RadarRingRepository radarRingRepository,
                        RadarCategoryRepository radarCategoryRepository,
                        RadarUserRepository radarUserRepository,
                        RadarTypeRepository radarTypeRepository)
    {
        this.radarRepository = radarRepository;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarUserRepository = radarUserRepository;
        this.radarTypeRepository = radarTypeRepository;
    }

    public Radar createDefault(RadarUser radarUser)
    {
        Radar retVal = new Radar();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setRadarUser(radarUser);
        retVal.setRadarItems(new ArrayList<RadarItem>());
        return retVal;
    }

    public Radar findById(Long radar)
    {
        return this.radarRepository.findOne(radar);
    }

    public List<Radar> findByRadarUserId(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findAllByRadarUser(foundUser.getId());
        }

        return retVal;
    }

    public List<Radar> findByRadarUserIdAndIsPublished(Long radarUserId, boolean isPublished)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findAllByRadarUserAndIsPublished(foundUser.getId(), isPublished);
        }

        return retVal;
    }

    public Technology findTechnologyById(Long technologyId)
    {
        return this.technologyRepository.findOne(technologyId);
    }

    public List<Radar> getAllByTechnologyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.radarRepository.findAllByTechnologyId(foundItem.getId());
        }

        return retVal;
    }

    public Radar addRadar(Long radarUserId, String name, Long radarTypeId)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            Radar radarInstance = this.radarRepository.findByIdAndName(radarUserId, name);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);
            RadarType radarType = this.radarTypeRepository.findOne(radarTypeId);

            if(radarInstance == null && radarUser != null && radarType!=null)
            {
                retVal = this.createDefault(radarUser);
                retVal.setName(name);
                retVal.setRadarType(radarType);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public boolean deleteRadar(Long radarUserId, Long radarInstanceId)
    {
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarInstanceId, radarUserId);

        if(radar!=null && radar.getIsPublished()==false && radar.getIsLocked()==false)
        {
            this.radarRepository.delete(radar.getId());
            retVal = true;
        }

        return retVal;
    }

    public Radar updateRadar(Long radarUserId, Long radarId, String name)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.radarRepository.findOne(radarId);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUserId)
            {
                retVal.setName(name);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, String technologyName, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(!technologyName.isEmpty())
        {
            Technology targetTechnology = this.technologyRepository.findByName(technologyName);

            if (targetTechnology == null)
            {
                targetTechnology = new Technology();
                targetTechnology.setName(technologyName);
                targetTechnology.setCreateDate(new Date());
                targetTechnology.setUrl(technologyUrl);
                targetTechnology.setCreator("");
                targetTechnology.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
            }

            retVal = this.addRadarItem(radarUser, radarId, targetTechnology, radarRingId, confidenceLevel, assessmentDetails);
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.radarRepository.findOne(radarId);

            if(retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                retVal.addRadarItem(targetTechnology, radarRing, confidenceLevel, assessmentDetails);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public Radar addRadarItems(RadarUser radarUser, Long radarId, List<RadarItemToBeAdded> radarItems)
    {
        Radar retVal = null;

        if(radarItems!=null)
        {
            retVal = this.radarRepository.findOne(radarId);

            if(retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
            {
                for(int i = 0; i < radarItems.size(); i++)
                {
                    RadarRing radarRing = this.radarRingRepository.findOne(radarItems.get(i).getRadarRingId());
                    Technology targetTechnology = this.findTechnologyById(radarItems.get(i).getTechnologyId());
                    retVal.addRadarItem(targetTechnology, radarRing, radarItems.get(i).getConfidenceFactor(), radarItems.get(i).getDetails());
                    this.radarRepository.save(retVal);
                }
            }
        }

        return retVal;
    }

    public RadarItem updateRadarItem(Long radarId, Long radarItemId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarItem retVal = null;

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0)
        {
            Radar radar = this.radarRepository.findOne(radarId);

            if(radar != null && radar.getIsLocked() == false)
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                radar.updateRadarItem(radarItemId, radarRing, confidenceLevel, assessmentDetails);
                this.radarRepository.save(radar);
            }
        }
        return retVal;
    }

    public boolean deleteRadarItem(Long radarId, Long radarItemId, Long radarUserId)
    {
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, radarUserId);

        if(radar!=null && radar.getIsLocked() == false)
        {
            radar.removeRadarItem(radarItemId);
            this.radarRepository.save(radar);
            retVal = true;
        }
        return retVal;
    }

    public boolean deleteRadarItems(Long userId, Long radarId, List<Long> radarItemIds)
    {
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, userId);

        if(radar!=null && radar.getIsLocked() == false)
        {
            for(int i =0; i < radarItemIds.size(); i++)
            {
                radar.removeRadarItem(radarItemIds.get(i));
            }

            this.radarRepository.save(radar);
            retVal = true;
        }
        return retVal;
    }

    public Radar findMostRecentByUserIdAndPublished(Long userId, boolean publishedOnly)
    {
        return this.radarRepository.findMostRecentByUserIdAndPublishedOnly(userId, publishedOnly);
    }

    public boolean publishRadar(Long userId, Long radarId, boolean shouldPublish)
    {
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, userId);

        if(radar!=null && radar.getIsLocked() == false)
        {
            radar.setIsPublished(shouldPublish);
            this.radarRepository.save(radar);
            retVal = true;
        }

        return retVal;
    }

    public boolean lockRadar(Long userId, Long radarId, boolean shouldLock)
    {
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, userId);

        if(radar!=null)
        {
            radar.setIsLocked(shouldLock);
            this.radarRepository.save(radar);
            retVal = true;
        }

        return retVal;
    }
}
