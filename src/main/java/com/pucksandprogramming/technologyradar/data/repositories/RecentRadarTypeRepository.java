package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RecentRadarTypeRepository extends RadarTypeRepositoryBase
{
    public RecentRadarTypeRepository()
    {

    }

    @Override
    public List<RadarType> findByUser(Long userId)
    {
        Query query = entityManager.createNamedQuery("owned_FindMostRecentRadarTypeByRadarUserId");
        query.setParameter("radarUserId", userId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public List<RadarType> findByUserAndRadarType(Long userId, String radarTypeId)
    {
        Query query = entityManager.createNamedQuery("owned_findMostRecentByRadarUserIdAndId");
        query.setParameter("radarUserId", userId);
        query.setParameter("radarTypeId", radarTypeId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public List<RadarType> findSharedRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query =  this.entityManager.createNamedQuery("public_FindMostRecentTypesByPublishedRadarsUserIdAndRadarTypeId");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("owned_FindHistorySharedRadarTypesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }


    public List<RadarType> findAllAssociatedRadarTypes(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllAssociated");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }


//    public List<RadarType> findOthersRadarTypes(Long radarUserId)
//    {
//        Query query = entityManager.createNamedQuery("findOthersRadarTypes");
//        query.setParameter("radarUserId", radarUserId);
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }

//    public List<RadarType> findAllForPublishedRadars()
//    {
//        Query query = entityManager.createNamedQuery("findAllForPublishedRadars");
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }

//    public List<RadarType> findAllForPublishedRadars(Long radarUserId)
//    {
//        Query query = entityManager.createNamedQuery("findAllForPublishedRadarsExcludeUser");
//        query.setParameter("radarUserId", radarUserId);
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }

    private boolean shouldVersion(RadarType source, RadarTypeEntity destination)
    {
        boolean retVal = false;

        if(source.getRadarCategories().size() != destination.getRadarCategories().size())
        {
            retVal = true;
        }
        else
        {
            retVal = this.hasRadarCategoryBeenAdded(source, destination);

            if (retVal == false)
            {
                retVal = this.hasRadarCategoryBeenRemoved(source, destination);
            }
        }

        if(retVal==false)
        {
            if (source.getRadarRings().size() != destination.getRadarRings().size())
            {
                retVal = true;
            }
            else
            {
                retVal = this.hasRadarRingBeenAdded(source, destination);

                if (retVal == false)
                {
                    retVal = this.hasRadarRingBeenRemoved(source, destination);
                }
            }
        }

        return retVal;
    }

    private boolean hasRadarCategoryBeenAdded(RadarType source, RadarTypeEntity destination)
    {
        boolean retVal = false;

        for(RadarCategory radarCategory : source.getRadarCategories())
        {
            boolean foundMatch = false;

            for (RadarCategoryEntity radarCategoryEntity : destination.getRadarCategories())
            {
                if (radarCategory.getId() == radarCategoryEntity.getId())
                {
                    foundMatch = true;
                    break;
                }
            }

            if(foundMatch==false)
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    private boolean hasRadarCategoryBeenRemoved(RadarType source, RadarTypeEntity destination)
    {
        boolean retVal = false;

        for(RadarCategoryEntity radarCategoryEntity : destination.getRadarCategories())
        {
            boolean foundMatch = false;

            for (RadarCategory radarCategory : source.getRadarCategories())
            {
                if (radarCategory.getId() == radarCategoryEntity.getId())
                {
                    foundMatch = true;
                    break;
                }
            }

            if(foundMatch==false)
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    private boolean hasRadarRingBeenAdded(RadarType source, RadarTypeEntity destination)
    {
        boolean retVal = false;

        for(RadarRing radarRing : source.getRadarRings())
        {
            boolean foundMatch = false;

            for (RadarRingEntity radarRingEntity : destination.getRadarRings())
            {
                if (radarRing.getId() == radarRingEntity.getId())
                {
                    foundMatch = true;
                    break;
                }
            }

            if(foundMatch==false)
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    private boolean hasRadarRingBeenRemoved(RadarType source, RadarTypeEntity destination)
    {
        boolean retVal = false;

        for(RadarRingEntity radarRingEntity : destination.getRadarRings())
        {
            boolean foundMatch = false;

            for (RadarRing radarRing : source.getRadarRings())
            {
                if (radarRing.getId() == radarRingEntity.getId())
                {
                    foundMatch = true;
                    break;
                }
            }

            if(foundMatch==false)
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    private RadarTypeEntity getNextVersionNumber(Long radarUserId, RadarTypeEntity instanceToId)
    {
        if(instanceToId.getVersionedId().getId()==null || instanceToId.getVersionedId().getId()=="")
        {
            instanceToId.getVersionedId().setId(UUID.randomUUID().toString());
            instanceToId.getVersionedId().setVersion(1L);
        }
        else
        {
            Query query = entityManager.createNamedQuery("findMostRecentByUserAndId");
            query.setParameter("radarUserId", radarUserId);
            query.setParameter("radarTypeId", instanceToId.getVersionedId().getId());
            List<RadarTypeEntity> foundItems = query.getResultList();

            if (foundItems != null && foundItems.size() > 0)
            {
                instanceToId.getVersionedId().setVersion(foundItems.get(0).getVersionedId().getVersion() + 1);
            }
        }

        return instanceToId;
    }

    @Override
    public RadarType save(RadarType itemToSave)
    {
        RadarTypeEntity radarTypeEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            boolean shouldVersion = false;

            if (itemToSave.getId()!=null && itemToSave.getId()!="")
            {
                VersionedIdEntity versionedIdEntity = new VersionedIdEntity(itemToSave.getId(), itemToSave.getVersion());
                radarTypeEntity = this.entityRepository.findOne(versionedIdEntity);
                shouldVersion = this.shouldVersion(itemToSave, radarTypeEntity);
            }
            else
            {
                radarTypeEntity = new RadarTypeEntity();
                radarTypeEntity.getVersionedId().setId(UUID.randomUUID().toString());
                radarTypeEntity.getVersionedId().setVersion(1L);
                shouldVersion = true;
            }

            if(shouldVersion)
            {
                radarTypeEntity = new RadarTypeEntity();

                // THe mapper doesn't overwrite an instance so I keep getting transient errors
                // for now manually map it, and later look for another mapper
                ///.... this sucks
                if (radarTypeEntity != null)
                {
                    radarTypeEntity = this.modelMapper.map(itemToSave, RadarTypeEntity.class);
                    radarTypeEntity.getVersionedId().setId(itemToSave.getId());
                    radarTypeEntity = this.getNextVersionNumber(itemToSave.getRadarUser().getId(), radarTypeEntity);

                    for(RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings())
                    {
                        radarRingEntity.setRadarType(radarTypeEntity);
                        radarRingEntity.setId(-1L);
                    }

                    for(RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories())
                    {
                        radarCategoryEntity.setRadarType(radarTypeEntity);
                        radarCategoryEntity.setId(-1L);
                    }

                    radarTypeEntity = this.entityRepository.save(radarTypeEntity);
                }
            }
            else
            {
                radarTypeEntity.setName(itemToSave.getName());
                radarTypeEntity.setIsPublished((itemToSave.getIsPublished()));

                for(RadarRing radarRing : itemToSave.getRadarRings())
                {
                    for (RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings())
                    {
                        if (radarRing.getId() == radarRingEntity.getId())
                        {
                            radarRingEntity.setName(radarRing.getName());
                            radarRingEntity.setDisplayOrder(radarRing.getDisplayOrder());
                            break;
                        }
                    }
                }

                for(RadarCategory radarCategory : itemToSave.getRadarCategories())
                {
                    for (RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories())
                    {
                        if (radarCategory.getId() == radarCategoryEntity.getId())
                        {
                            radarCategoryEntity.setName(radarCategory.getName());
                            radarCategoryEntity.setColor(radarCategory.getColor());
                            break;
                        }
                    }
                }

                radarTypeEntity = this.entityRepository.save(radarTypeEntity);
            }
        }

        return this.modelMapper.map(radarTypeEntity, RadarType.class);
    }

    public boolean saveAssociatedRadarType(RadarUser radarUser, RadarType radarType)
    {
        boolean retVal = false;

        if (radarType != null && radarUser != null)
        {
            AssociatedRadarTypeEntity associatedRadarTypeEntityadarTypeEntity = this.associatedRadarTypeDAO.findByRadarUserIdAndRadarTypeId(radarUser.getId(), radarType.getId());

            if(associatedRadarTypeEntityadarTypeEntity==null)
            {
                associatedRadarTypeEntityadarTypeEntity = new AssociatedRadarTypeEntity();
                associatedRadarTypeEntityadarTypeEntity.setRadarUserId(radarUser.getId());
                associatedRadarTypeEntityadarTypeEntity.setRadarTypeId(radarType.getId());
                associatedRadarTypeEntityadarTypeEntity.setRadarTypeVersion(radarType.getVersion());
                associatedRadarTypeEntityadarTypeEntity = this.associatedRadarTypeDAO.save(associatedRadarTypeEntityadarTypeEntity);

                if(associatedRadarTypeEntityadarTypeEntity.getId() > 0)
                {
                    retVal = true;
                }
            }
            else
            {
                retVal = true;
            }
        }

        return retVal;
    }

    public boolean deleteAssociatedRadarType(RadarUser radarUser, RadarType radarType)
    {
        boolean retVal = false;

        if (radarType != null && radarUser != null)
        {
            AssociatedRadarTypeEntity radarTypeEntity = this.associatedRadarTypeDAO.findByRadarUserIdAndRadarTypeId(radarUser.getId(), radarType.getId());

            if (radarTypeEntity != null)
            {
                this.associatedRadarTypeDAO.delete(radarTypeEntity.getId());
                retVal = true;
            }
        }

        return retVal;
    }
}

