package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.RadarCategoryDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarRingDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarTypeDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RadarTypeRepository extends SimpleDomainRepository<RadarType, RadarTypeEntity, RadarTypeDAO, Long> {
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    RadarUserDAO radarUserDAO;

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
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<RadarType> findAllByUserId(Long radarUserId)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        Iterable<RadarTypeEntity> foundItems = this.entityRepository.findAllByCreatorId(radarUserId);

        for (RadarTypeEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarType.class));
        }

        return retVal;
    }

    public List<RadarType> findAllAssociatedRadarTypes(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("findAllAssociated");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarType> findAllByIsPublished(boolean isPublished)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        Iterable<RadarTypeEntity> foundItems = this.entityRepository.findAllByIsPublished(isPublished);

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

    @Override
    public RadarType save(RadarType itemToSave)
    {
        RadarTypeEntity radarTypeEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null) {
            if (itemToSave.getId() > 0) {
                radarTypeEntity = this.entityRepository.findOne(itemToSave.getId());
            } else {
                radarTypeEntity = new RadarTypeEntity();
            }

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (radarTypeEntity != null) {
                radarTypeEntity.setName(itemToSave.getName());
                radarTypeEntity.setIsPublished(itemToSave.getIsPublished());
                radarTypeEntity.setCreator(radarUserDAO.findOne(itemToSave.getCreator().getId()));

                // save it here so we can add the rings and categories.  Not sure how else to do this.  Doesn't feel right though.
                radarTypeEntity = this.entityRepository.saveAndFlush(radarTypeEntity);

                // process Radar Rings
                // First remove any deletions
                if (radarTypeEntity.getRadarRings() != null) {
                    for (RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings()) {
                        boolean foundMatch = false;

                        for (RadarRing radarRing : itemToSave.getRadarRings()) {
                            if (radarRing.getId() == radarRingEntity.getId()) {
                                foundMatch = true;
                                // match found, overwrite changes
                                radarRingEntity.setName(radarRing.getName());
                                radarRingEntity.setDisplayOrder(radarRing.getDisplayOrder());
                            }
                        }

                        // it wasn't found in both lists, delete it
                        if (foundMatch == false) {
                            radarTypeEntity.getRadarRings().remove(radarRingEntity);
                            radarRingDAO.delete(radarRingEntity);
                        }
                    }
                }

                // then add in any new ones
                for (RadarRing radarRing : itemToSave.getRadarRings()) {
                    boolean foundMatch = false;

                    if(radarTypeEntity.getRadarRings()!=null) {
                        for (RadarRingEntity radarRingEntity : radarTypeEntity.getRadarRings()) {
                            if (radarRingEntity.getId() == radarRing.getId()) {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else{
                        radarTypeEntity.setRadarRings(new ArrayList<RadarRingEntity>());
                    }

                    if (foundMatch == false) {
                        RadarRingEntity newItem = new RadarRingEntity();
                        newItem.setName(radarRing.getName());
                        newItem.setDisplayOrder(radarRing.getDisplayOrder());
                        newItem.setRadarType(radarTypeEntity);
                        this.radarRingDAO.save(newItem);
                        radarTypeEntity.getRadarRings().add(newItem);
                    }
                }

                // process Radar Categories
                // First remove any deletions
                if(radarTypeEntity.getRadarCategories() != null){
                    for (RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories()) {
                        boolean foundMatch = false;

                        for (RadarCategory radarCategory : itemToSave.getRadarCategories()) {
                            if (radarCategory.getId() == radarCategoryEntity.getId()) {
                                foundMatch = true;
                                // match found, overwrite changes
                                radarCategoryEntity.setName(radarCategory.getName());
                                radarCategoryEntity.setColor(radarCategory.getColor());
                            }
                        }

                        // it wasn't found in both lists, delete it
                        if (foundMatch == false) {
                            radarTypeEntity.getRadarCategories().remove(radarCategoryEntity);
                            radarCategoryDAO.delete(radarCategoryEntity);
                        }
                    }
                }

                // then add in any new ones
                for (RadarCategory radarCategory : itemToSave.getRadarCategories()) {
                    boolean foundMatch = false;

                    if(radarTypeEntity.getRadarCategories()!=null) {
                        for (RadarCategoryEntity radarCategoryEntity : radarTypeEntity.getRadarCategories()) {
                            if (radarCategoryEntity.getId() == radarCategory.getId()) {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else{
                        radarTypeEntity.setRadarCategories(new ArrayList<RadarCategoryEntity>());
                    }

                    if (foundMatch == false) {
                        RadarCategoryEntity newItem = new RadarCategoryEntity();
                        newItem.setName(radarCategory.getName());
                        newItem.setColor(radarCategory.getColor());
                        newItem.setRadarType(radarTypeEntity);
                        this.radarCategoryDAO.save(newItem);
                        radarTypeEntity.getRadarCategories().add(newItem);
                    }
                }
            }

            if (radarTypeEntity != null) {
                this.entityRepository.save(radarTypeEntity);
            }
        }

        return this.modelMapper.map(radarTypeEntity, RadarType.class);
    }
}
