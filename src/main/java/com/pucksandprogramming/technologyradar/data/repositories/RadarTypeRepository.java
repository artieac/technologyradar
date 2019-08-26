package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Repository
public class RadarTypeRepository extends SimpleDomainRepository<RadarType, RadarTypeEntity, RadarTypeDAO, Long>
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
    RadarDAO radarDAO;

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
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<RadarType> findByUser(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        List<RadarTypeEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndStateOrderByCreateDate(userId, 1);

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
        Query query =  this.entityManager.createNamedQuery("public_findSharedRadarTypes");
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

    @Override
    public RadarType save(RadarType itemToSave)
    {
        RadarTypeEntity radarTypeEntity = null;

        if(itemToSave !=null)
        {
            if (itemToSave.getId() != null && itemToSave.getId() > 0)
            {
                radarTypeEntity = this.entityRepository.findOne(itemToSave.getId());
            }
            else
            {
                radarTypeEntity = new RadarTypeEntity();
            }

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (radarTypeEntity != null)
            {
                radarTypeEntity.setName(itemToSave.getName());
                radarTypeEntity.setIsPublished(itemToSave.getIsPublished());
                radarTypeEntity.setDescription(itemToSave.getDescription());
                radarTypeEntity.setState(itemToSave.getState());
                radarTypeEntity.setRadarUser(radarUserDAO.findOne(itemToSave.getRadarUser().getId()));

                // save it here so we can add the rings and categories.  Not sure how else to do this.  Doesn't feel right though.
                radarTypeEntity = this.entityRepository.saveAndFlush(radarTypeEntity);

                // Some ground rules.  If it has any radar's associated with it you can't remove any radar rings.
                List<RadarEntity> createdRadars = this.radarDAO.findAllByRadarUserIdAndRadarTypeId(itemToSave.getRadarUser().getId(), itemToSave.getId());

                if(createdRadars==null || createdRadars.size()==0)
                {
                    // process Radar Rings
                    if (radarTypeEntity.getRadarRings() != null)
                    {
                        for (RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings())
                        {
                            boolean foundMatch = false;

                            for (RadarRing radarRing : itemToSave.getRadarRings())
                            {
                                if (radarRing.getId() == radarRingEntity.getId())
                                {
                                    foundMatch = true;
                                    // match found, overwrite changes
                                    radarRingEntity.setName(radarRing.getName());
                                    radarRingEntity.setDisplayOrder(radarRing.getDisplayOrder());
                                }
                            }

                            // it wasn't found in both lists, delete it
                            if (foundMatch == false)
                            {
                                radarTypeEntity.getRadarRings().remove(radarRingEntity);
                                radarRingDAO.delete(radarRingEntity);
                            }
                        }
                    }

                    // process Radar Categories
                    // First remove any deletions
                    if(radarTypeEntity.getRadarCategories() != null)
                    {
                        for (RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories())
                        {
                            boolean foundMatch = false;

                            for (RadarCategory radarCategory : itemToSave.getRadarCategories())
                            {
                                if (radarCategory.getId() == radarCategoryEntity.getId())
                                {
                                    foundMatch = true;
                                    // match found, overwrite changes
                                    radarCategoryEntity.setName(radarCategory.getName());
                                    radarCategoryEntity.setColor(radarCategory.getColor());
                                }
                            }

                            // it wasn't found in both lists, delete it
                            if (foundMatch == false)
                            {
                                radarTypeEntity.getRadarCategories().remove(radarCategoryEntity);
                                radarCategoryDAO.delete(radarCategoryEntity);
                            }
                        }
                    }
                }

                // then add in any new ones
                for (RadarRing radarRing : itemToSave.getRadarRings())
                {
                    boolean foundMatch = false;

                    if(radarTypeEntity.getRadarRings()!=null)
                    {
                        for (RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings())
                        {
                            if (radarRingEntity.getId() == radarRing.getId())
                            {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        radarTypeEntity.setRadarRings(new ArrayList<RadarRingEntity>());
                    }

                    if (foundMatch == false)
                    {
                        RadarRingEntity newItem = new RadarRingEntity();
                        newItem.setName(radarRing.getName());
                        newItem.setDisplayOrder(radarRing.getDisplayOrder());
                        newItem.setRadarType(radarTypeEntity);
                        this.radarRingDAO.save(newItem);
                        radarTypeEntity.getRadarRings().add(newItem);
                    }
                }

                // then add in any new ones
                for (RadarCategory radarCategory : itemToSave.getRadarCategories())
                {
                    boolean foundMatch = false;

                    if(radarTypeEntity.getRadarCategories()!=null)
                    {
                        for (RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories())
                        {
                            if (radarCategoryEntity.getId() == radarCategory.getId())
                            {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        radarTypeEntity.setRadarCategories(new ArrayList<RadarCategoryEntity>());
                    }

                    if (foundMatch == false)
                    {
                        RadarCategoryEntity newItem = new RadarCategoryEntity();
                        newItem.setName(radarCategory.getName());
                        newItem.setColor(radarCategory.getColor());
                        newItem.setRadarType(radarTypeEntity);
                        this.radarCategoryDAO.save(newItem);
                        radarTypeEntity.getRadarCategories().add(newItem);
                    }
                }
            }

            if (radarTypeEntity != null)
            {
                this.entityRepository.save(radarTypeEntity);
            }
        }

        return this.modelMapper.map(radarTypeEntity, RadarType.class);
    }

    public boolean saveAssociatedRadarType(RadarUser radarUser, RadarType radarType)
    {
        boolean retVal = false;

        if (radarType != null && radarUser != null)
        {
            AssociatedRadarTypeEntity associatedRadarTypeEntityRadarTypeEntity = this.associatedRadarTypeDAO.findByRadarUserIdAndRadarTypeId(radarUser.getId(), radarType.getId());

            if(associatedRadarTypeEntityRadarTypeEntity==null)
            {
                associatedRadarTypeEntityRadarTypeEntity = new AssociatedRadarTypeEntity();
                associatedRadarTypeEntityRadarTypeEntity.setRadarUserId(radarUser.getId());
                associatedRadarTypeEntityRadarTypeEntity.setRadarTypeId(radarType.getId());
                associatedRadarTypeEntityRadarTypeEntity = this.associatedRadarTypeDAO.save(associatedRadarTypeEntityRadarTypeEntity);

                if(associatedRadarTypeEntityRadarTypeEntity.getId() > 0)
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

