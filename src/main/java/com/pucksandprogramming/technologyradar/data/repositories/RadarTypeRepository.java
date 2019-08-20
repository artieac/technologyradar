package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RadarTypeRepository extends SimpleDomainRepository<RadarType, RadarTypeEntity, RadarTypeDAO, VersionedIdEntity>
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    AssociatedRadarTypeDAO associatedRadarTypeDAO;

    @Autowired
    public void setEntityRepository(RadarTypeDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public RadarTypeRepository() {
        super(RadarType.class);
    }

    public List<RadarType> mapList(List<RadarTypeEntity> source)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(int i = 0; i < source.size(); i++)
            {
                RadarType newItem = this.modelMapper.map(source.get(i), RadarType.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected RadarTypeEntity findOne(RadarType domainModel)
    {
        VersionedIdEntity idEntity = new VersionedIdEntity(domainModel.getId(), domainModel.getVersion());
        return this.entityRepository.findOne(idEntity);
    }

    public RadarType findOne(String radarTypeId, Long version)
    {
        RadarType retVal = null;

        VersionedIdEntity idEntity = new VersionedIdEntity(radarTypeId, version);
        RadarTypeEntity foundItem = this.entityRepository.findOne(idEntity);

        if(foundItem!=null)
        {
            retVal = this.modelMapper.map(foundItem, RadarType.class);
        }

        return retVal;
    }

    public List<RadarType> findByUser(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        List<RadarTypeEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndStateOrderByVersionedId(userId, 1);

        if(foundItems != null)
        {
            for (RadarTypeEntity foundItem : foundItems)
            {
                retVal.add(this.modelMapper.map(foundItem, RadarType.class));
            }
        }

        return retVal;
    }

    public List<RadarType> findByUserAndRadarType(Long userId, String radarTypeId)
    {
        Query query = entityManager.createNamedQuery("owned_FindHistoryByRadarUserIdAndId");
        query.setParameter("radarUserId", userId);
        query.setParameter("radarTypeId", radarTypeId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findSharedRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("owned_FindHistorySharedRadarTypesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findSharedRadarTypes()
    {
        Query query =  this.entityManager.createNamedQuery("public_findMostRecentSharedRadarTypes");
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAssociatedRadarTypes(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllAssociated");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }


    public List<RadarType> findByPublishedRadars()
    {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadars");
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findByPublishedRadarsExcludeUser(Long excludeUserId)
    {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadarsExcludeUser");
        query.setParameter("radarUserId", excludeUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findOwnedWithRadars(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllOwnedWithRadars");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

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
                radarTypeEntity.setDescription(itemToSave.getDescription());
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
            AssociatedRadarTypeEntity associatedRadarTypeEntityadarTypeEntity = this.associatedRadarTypeDAO.findByRadarUserIdAndRadarTypeIdAndRadarTypeVersion(radarUser.getId(), radarType.getId(), radarType.getVersion());

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

