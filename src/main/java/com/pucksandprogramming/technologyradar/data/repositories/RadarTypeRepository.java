package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Version;
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

    private List<RadarType> mapList(List<RadarTypeEntity> source)
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

    public RadarType findOne(Long radarTypeId, Long version)
    {
        VersionedIdEntity idEntity = new VersionedIdEntity(radarTypeId, version);
        RadarTypeEntity foundItem = this.entityRepository.findOne(idEntity);

        return this.modelMapper.map(foundItem, RadarType.class);
    }

    public List<RadarType> findAllRadarTypeVersionsForUser(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        List<RadarTypeEntity> foundItems = this.entityRepository.findAllByRadarUserIdOrderByVersionedId(userId);

        if(foundItems != null)
        {
            for (RadarTypeEntity foundItem : foundItems)
            {
                retVal.add(this.modelMapper.map(foundItem, RadarType.class));
            }
        }

        return retVal;
    }

    public List<RadarType> findMostRecentByUserAndIsPublished(Long radarUserId)
    {
        Query query =  this.entityManager.createNamedQuery("findMostRecentTypesByPublishedRadars");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAllTypesByUserAandPublishedRadars(Long userId)
    {
        Query query = entityManager.createNamedQuery("findAllTypesByPublishedRadars");
        query.setParameter("radarUserId", userId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findMostRecentRadarTypesForUser(Long userId)
    {
        Query query = entityManager.createNamedQuery("findAllMostRecentByUser");
        query.setParameter("radarUserId", userId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findHistoryForRadarType(Long userId, Long radarTypeId)
    {
        Query query = entityManager.createNamedQuery("findHistoryByRadarUserIdAndId");
        query.setParameter("radarUserId", userId);
        query.setParameter("radarTypeId", radarTypeId);
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

    public List<RadarType> findAllSharedRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllSharedRadarTypesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAllByIsPublished(boolean isPublished)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        Iterable<RadarTypeEntity> foundItems = null;//this.entityRepository.findAllByIsPublished(isPublished);

        for (RadarTypeEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarType.class));
        }

        return retVal;
    }

    public List<RadarType> findOthersRadarTypes(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findOthersRadarTypes");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAllForPublishedRadars()
    {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadars");
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAllForPublishedRadars(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadarsExcludeUser");
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

    private Long getNextVersionNumber(Long radarUserId, Long radarTypeId)
    {
        Long retVal = 1L;

        Query query = entityManager.createNamedQuery("findMostRecentByUserAndId");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTypeId", radarTypeId);
        List<RadarTypeEntity> foundItems = query.getResultList();

        if(foundItems!=null && foundItems.size() > 0)
        {
            retVal = foundItems.get(0).getVersionedId().getVersion();
            retVal += 1;
        }

        return retVal;
    }

    @Override
    public RadarType save(RadarType itemToSave)
    {
        RadarTypeEntity radarTypeEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            if (itemToSave.getId() > 0)
            {
                VersionedIdEntity versionedIdEntity = new VersionedIdEntity(itemToSave.getId(), itemToSave.getVersion());
                radarTypeEntity = this.entityRepository.findOne(versionedIdEntity);
            }
            else
            {
                radarTypeEntity = new RadarTypeEntity();
            }

            boolean shouldVersion = this.shouldVersion(itemToSave, radarTypeEntity);

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
                    radarTypeEntity.getVersionedId().setVersion(this.getNextVersionNumber(itemToSave.getRadarUser().getId(), itemToSave.getId()));

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
