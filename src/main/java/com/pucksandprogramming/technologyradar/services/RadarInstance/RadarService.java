package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarItemToBeAdded;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */

@Component
public class RadarService extends ServiceBase
{
    protected RadarRepositoryFactory radarRepositoryFactory;
    protected TechnologyRepository technologyRepository;
    protected RadarRingRepository radarRingRepository;
    protected RadarCategoryRepository radarCategoryRepository;
    protected RadarAccessManager radarAccessManager;
    protected RadarTypeRepository radarTypeRepository;

    public RadarService(RadarRepositoryFactory radarRepositoryFactory,
                        RadarTypeRepository radarTypeRepository,
                        TechnologyRepository technologyRepository,
                        RadarRingRepository radarRingRepository,
                        RadarCategoryRepository radarCategoryRepository,
                        RadarUserRepository radarUserRepository,
                        RadarAccessManager radarAccessManager)
    {
        super(radarUserRepository);

        this.radarRepositoryFactory = radarRepositoryFactory;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarAccessManager = radarAccessManager;
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

    public Radar findById(Long radarId)
    {
        return this.radarRepositoryFactory.getRadarRepository(null).findOne(radarId);
    }

    public List<Radar> findByRadarUserId(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();
        RadarUser targetDataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(targetDataOwner!=null)
        {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserId(targetDataOwner.getId());
        }

        return retVal;
    }

    public Radar findByUserAndRadarId(Long radarUserId, Long radarId)
    {
        Radar retVal = null;
        RadarUser targetDataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(targetDataOwner!=null)
        {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserRadarId(targetDataOwner.getId(), radarId);
        }

        return retVal;
    }

    public List<Radar> findByUserAndType(Long radarUserId, Long radarTypeId)
    {
        List<Radar> retVal = new ArrayList<>();
        RadarUser targetDataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(targetDataOwner!=null)
        {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserAndType(targetDataOwner.getId(), radarTypeId);
        }

        return retVal;
    }

    public List<Radar> findByUserTypeAndVersion(Long radarUserId, Long radarTypeId)
    {
        List<Radar> retVal = new ArrayList<>();
        RadarUser targetDataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(targetDataOwner!=null)
        {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserAndType(targetDataOwner.getId(), radarTypeId);
        }

        return retVal;
    }

    public List<Radar> getAllByRadarSubjectId(Long radarSubjectId)
    {
        return this.radarRepositoryFactory.getRadarRepository(null).findByRadarSubjectId(radarSubjectId);
    }

    public List<Radar> getAllNotOwnedByTechnologyId(Long radarUserId, Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);
        RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(foundItem!=null)
        {
            if (dataOwner != null)
            {
                retVal = this.radarRepositoryFactory.getRadarRepository(null).findNotOwnedByRadarSubjectAndUser(dataOwner.getId(), foundItem.getId());
            }
            else
            {
                retVal = this.radarRepositoryFactory.getRadarRepository(null).findByRadarSubjectId(foundItem.getId());
            }
        }

        return retVal;
    }

    public List<Radar> getAllOwnedByTechnologyId(Long radarUserId, Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);
        RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

        if(foundItem!=null && dataOwner != null)
        {
            retVal = this.radarRepositoryFactory.getRadarRepository(dataOwner).findOwnedByTechnologyId(dataOwner.getId(), foundItem.getId());
        }

        return retVal;
    }

    public Integer getSharedRadarCount(Long radarUserId)
    {
        Integer retVal = 0;

        if(this.getAuthenticatedUser()!=null)
        {
            RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

            List<Radar> publishedRadars = this.radarRepositoryFactory.getRadarRepository(dataOwner).findAllPublishedRadarsByUser(this.getAuthenticatedUser().getUserId());

            if(publishedRadars!=null)
            {
                retVal = publishedRadars.size();
            }
        }

        return retVal;
    }

    public Radar findCurrentByType(Long radarUserId, Long radarTypeId)
    {
        Radar retVal = null;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);
        RadarType radarType = this.radarTypeRepository.findOne(radarTypeId);

        if(dataOwner != null)
        {
            List<RadarItem> foundItems = this.radarRepositoryFactory.getRadarRepository(dataOwner).findCurrentByType(radarUserId, radarTypeId);

            retVal = new Radar();
            retVal.setAssessmentDate(new Date());
            retVal.setRadarType(radarType);
            retVal.setIsLocked(true);
            retVal.setIsPublished(true);
            retVal.setName("Current");
            retVal.setRadarItems(foundItems);
            retVal.setRadarUser(dataOwner);
            retVal.setId(-1L);
        }

        return retVal;
    }

    public Radar addRadar(Long radarUserId, String name, Long radarTypeId)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

            if(this.radarAccessManager.canModifyRadar(dataOwner))
            {
                RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

                RadarType radarType = this.radarTypeRepository.findOne(radarTypeId);

                if (dataOwner != null && radarType != null)
                {
                    retVal = this.createDefault(dataOwner);
                    retVal.setName(name);
                    retVal.setRadarType(radarType);
                    radarRepository.save(retVal);
                }
            }
        }

        return retVal;
    }

    public boolean deleteRadar(Long radarUserId, Long radarInstanceId)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Radar radar = radarRepository.findByUserRadarId(radarUserId, radarInstanceId);

        if(radar!=null && radar.getIsPublished()==false && radar.getIsLocked()==false)
        {
            if(this.radarAccessManager.canModifyRadar(dataOwner))
            {
                radarRepository.delete(radar.getId());
                retVal = true;
            }
        }

        return retVal;
    }

    public Radar updateRadar(Long radarUserId, Long radarId, String name)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.findById(radarId);
            RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

            if(this.radarAccessManager.canModifyRadar(dataOwner))
            {
                if (retVal != null && retVal.getIsLocked() == false)
                {
                    retVal.setName(name);
                    this.radarRepositoryFactory.getRadarRepository(dataOwner).save(retVal);
                }
            }
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, String technologyName, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(this.radarAccessManager.canModifyRadar(radarUser))
        {
            if (!technologyName.isEmpty())
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
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(this.radarAccessManager.canModifyRadar(radarUser))
        {
            if (targetTechnology != null)
            {
                retVal = this.findById(radarId);

                if (retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
                {
                    RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                    RadarCategory radarCategory = this.radarCategoryRepository.findOne(radarCategoryId);

                    if (retVal.getRadarType().hasRadarRing(radarRing) && retVal.getRadarType().hasRadarCategory(radarCategory))
                    {
                        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(radarUser);

                        Integer itemState = RadarItem.State_New;

                        RadarItem radarItemToAdd = new RadarItem(-1L, targetTechnology, radarCategory, radarRing, confidenceLevel, assessmentDetails);
                        RadarItem previousRadarItem = radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(retVal.getRadarUser().getId(), retVal.getId(), targetTechnology.getId());

                        radarItemToAdd.determineState(previousRadarItem);
                        retVal.addRadarItem(radarItemToAdd);

                        radarRepository.save(retVal);
                    }
                }
            }
        }

        return retVal;
    }

    public Radar addRadarItems(RadarUser radarUser, Long radarId, List<RadarItemToBeAdded> radarItems)
    {
        Radar retVal = null;

        if(this.radarAccessManager.canModifyRadar(radarUser))
        {
            if (radarItems != null)
            {
                retVal = this.findById(radarId);

                if (retVal != null && retVal.getIsLocked() == false && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
                {
                    for (int i = 0; i < radarItems.size(); i++)
                    {
                        RadarItemToBeAdded currentItemToAdd = radarItems.get(i);
                        RadarRing radarRing = this.radarRingRepository.findOne(currentItemToAdd.getRadarRingId());
                        RadarCategory radarCategory = this.radarCategoryRepository.findOne(currentItemToAdd.getRadarCategoryId());
                        Technology targetTechnology = this.technologyRepository.findOne(currentItemToAdd.getTechnologyId());

                        RadarItem newRadarItem = new RadarItem(-1L, targetTechnology, radarCategory, radarRing, currentItemToAdd.getConfidenceFactor(), currentItemToAdd.getDetails());

                        retVal.addRadarItem(newRadarItem);
                    }

                    this.radarRepositoryFactory.getRadarRepository(radarUser).save(retVal);
                }
            }
        }

        return retVal;
    }

    public RadarItem updateRadarItem(Long radarId, Long radarItemId, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarItem retVal = null;

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0)
        {
            Radar radar = this.findById(radarId);

            if(radar != null && radar.getIsLocked() == false)
            {
                if(this.radarAccessManager.canModifyRadar(radar.getRadarUser()))
                {
                    RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(radar.getRadarUser());

                    RadarItem radarItemToUpdate = radar.findRadarItemById(radarItemId);
                    radarItemToUpdate.setId(radarItemId);
                    radarItemToUpdate.setRadarRing(this.radarRingRepository.findOne(radarRingId));
                    radarItemToUpdate.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
                    radarItemToUpdate.setConfidenceFactor(confidenceLevel);
                    radarItemToUpdate.setDetails(assessmentDetails);

                    RadarItem previousRadarItem = radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(radar.getRadarUser().getId(), radar.getId(), radarItemToUpdate.getTechnology().getId());
                    radarItemToUpdate.determineState(previousRadarItem);

                    radar.updateRadarItem(radarItemId, radarItemToUpdate);
                    radarRepository.save(radar);
                }
            }
        }
        return retVal;
    }

    public boolean deleteRadarItem(Long radarId, Long radarItemId, Long radarUserId)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(radarUserId);

        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Radar radar = radarRepository.findByUserRadarId(radarUserId, radarId);

        if(radar!=null && radar.getIsLocked() == false)
        {
            if(this.radarAccessManager.canModifyRadar(radar.getRadarUser()))
            {
                radar.removeRadarItem(radarItemId);
                radarRepository.save(radar);
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean deleteRadarItems(Long userId, Long radarId, List<Long> radarItemIds)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Radar radar = radarRepository.findByUserRadarId(userId, radarId);

        if(radar!=null && radar.getIsLocked() == false)
        {
            if(this.radarAccessManager.canModifyRadar(radar.getRadarUser()))
            {
                for (int i = 0; i < radarItemIds.size(); i++)
                {
                    radar.removeRadarItem(radarItemIds.get(i));
                }

                radarRepository.save(radar);
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean publishRadar(Long userId, Long radarId, boolean shouldPublish)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(this.radarAccessManager.canModifyRadar(dataOwner))
        {
            RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

            Radar radar = radarRepository.findByUserRadarId(userId, radarId);

            if (radar != null && radar.getIsLocked() == false && radar.getIsPublished() != shouldPublish)
            {
                List<Radar> currentPublishedRadars = radarRepository.findAllPublishedRadarsByUser(dataOwner.getId());

                if (currentPublishedRadars.size() >= dataOwner.howManyRadarsCanShare())
                {
                    // unpublish the oldest and then publish the one they want.
                    currentPublishedRadars.get(0).setIsPublished(false);
                    radarRepository.save(currentPublishedRadars.get(0));
                }

                radar.setIsPublished(shouldPublish);
                radarRepository.save(radar);
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean lockRadar(Long userId, Long radarId, boolean shouldLock)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Radar radar = radarRepository.findByUserRadarId(userId, radarId);

        if(radar!=null)
        {
            if (this.radarAccessManager.canModifyRadar(radar.getRadarUser()))
            {
                radar.setIsLocked(shouldLock);
                radarRepository.save(radar);
                retVal = true;
            }
        }

        return retVal;
    }
}
