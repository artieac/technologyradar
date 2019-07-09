package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarItemToBeAdded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
public abstract class RadarServiceBase
{
    protected TechnologyRepository technologyRepository;
    protected RadarRepository radarRepository;
    protected RadarRingRepository radarRingRepository;
    protected RadarCategoryRepository radarCategoryRepository;
    protected RadarUserRepository radarUserRepository;
    protected RadarTypeRepository radarTypeRepository;

    public RadarServiceBase(RadarRepository radarRepository,
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

    public abstract List<Radar> findByRadarUserId(Long radarUserId, boolean publishedOnly);
    public abstract Radar findByUserAndRadarId(Long radarUserId, Long radarId, boolean publishedOnly);
    public abstract List<Radar> findByUserAndType(Long radarUserId, String radarTypeId, boolean publishedOnly);
    public abstract List<Radar> findByUserTypeAndVersion(Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean publishedOnly);
    public abstract List<Radar> getAllNotOwnedByTechnologyId(Long technologyId, RadarUser currentUser);

    public Integer getSharedRadarCount(Long radarUserId)
    {
        Integer retVal = 0;

        List<Radar> publishedRadars = this.radarRepository.findAllByRadarUserAndIsPublished(radarUserId, true);

        if(publishedRadars!=null)
        {
            retVal = publishedRadars.size();
        }

        return retVal;
    }

    public List<Radar> getAllOwnedByTechnologyId(Long technologyId, RadarUser currentUser)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null && currentUser != null)
        {
            retVal = this.radarRepository.findAllByTechnolgyIdAndRadarUserId(foundItem.getId(), currentUser.getId());
        }

        return retVal;
    }

    public Radar addRadar(Long radarUserId, String name, String radarTypeId, Long radarVersion)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            Radar radarInstance = this.radarRepository.findByIdAndName(radarUserId, name);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);
            RadarType radarType = this.radarTypeRepository.findOne(radarTypeId, radarVersion);

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
                targetTechnology.setCreator(radarUser.getId().toString());
            }

            retVal = this.addRadarItem(radarUser, radarId, targetTechnology, radarCategoryId, radarRingId, confidenceLevel, assessmentDetails);
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.radarRepository.findOne(radarId);

            if(retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                RadarCategory radarCategory = this.radarCategoryRepository.findOne(radarCategoryId);

                if(retVal.getRadarType().hasRadarRing(radarRing) && retVal.getRadarType().hasRadarCategory(radarCategory))
                {
                    Integer itemState = RadarItem.State_New;

                    RadarItem radarItemToAdd = new RadarItem(-1L, targetTechnology, radarCategory, radarRing, confidenceLevel, assessmentDetails);
                    RadarItem previousRadarItem = this.radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(retVal.getRadarUser().getId(), retVal.getId(), targetTechnology.getId());

                    radarItemToAdd.determineState(previousRadarItem);
                    retVal.addRadarItem(radarItemToAdd);

                    this.radarRepository.save(retVal);
                }
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
                    RadarItemToBeAdded currentItemToAdd = radarItems.get(i);
                    RadarRing radarRing = this.radarRingRepository.findOne(currentItemToAdd.getRadarRingId());
                    RadarCategory radarCategory = this.radarCategoryRepository.findOne(currentItemToAdd.getRadarCategoryId());
                    Technology targetTechnology = this.technologyRepository.findOne(currentItemToAdd.getTechnologyId());

                    RadarItem newRadarItem = new RadarItem(-1L, targetTechnology, radarCategory, radarRing, currentItemToAdd.getConfidenceFactor(), currentItemToAdd.getDetails());

                    retVal.addRadarItem(newRadarItem);
                }

                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarItem updateRadarItem(Long radarId, Long radarItemId, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarItem retVal = null;

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0)
        {
            Radar radar = this.radarRepository.findOne(radarId);

            if(radar != null && radar.getIsLocked() == false)
            {
                RadarItem radarItemToUpdate = radar.findRadarItemById(radarItemId);
                radarItemToUpdate.setId(radarItemId);
                radarItemToUpdate.setRadarRing(this.radarRingRepository.findOne(radarRingId));
                radarItemToUpdate.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
                radarItemToUpdate.setConfidenceFactor(confidenceLevel);
                radarItemToUpdate.setDetails(assessmentDetails);

                RadarItem previousRadarItem = this.radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(radar.getRadarUser().getId(), radar.getId(), radarItemToUpdate.getTechnology().getId());
                radarItemToUpdate.determineState(previousRadarItem);

                radar.updateRadarItem(radarItemId, radarItemToUpdate);
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

    public boolean publishRadar(Long userId, Long radarId, boolean shouldPublish)
    {
        boolean retVal = false;

        RadarUser targetUser = this.radarUserRepository.findOne(userId);

        if(targetUser!=null)
        {
            Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, userId);

            if (radar != null && radar.getIsLocked() == false && radar.getIsPublished() != shouldPublish)
            {
                List<Radar> currentPublishedRadars = this.radarRepository.findAllByRadarUserAndIsPublished(userId, true);

                if (currentPublishedRadars.size() >= targetUser.howManyRadarsCanShare())
                {
                    // unpublish the oldest and then publish the one they want.
                    currentPublishedRadars.get(0).setIsPublished(false);
                    this.radarRepository.save(currentPublishedRadars.get(0));
                }

                radar.setIsPublished(shouldPublish);
                this.radarRepository.save(radar);
                retVal = true;
            }
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
