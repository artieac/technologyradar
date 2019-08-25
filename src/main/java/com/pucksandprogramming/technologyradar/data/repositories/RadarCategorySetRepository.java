package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategoryEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarCategorySetEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingSetEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarCategorySetDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarRingSetDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategory;
import com.pucksandprogramming.technologyradar.domainmodel.RadarCategorySet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RadarCategorySetRepository extends SimpleDomainRepository<RadarCategorySet, RadarCategorySetEntity, RadarCategorySetDAO, Long>
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    public void setEntityRepository(RadarCategorySetDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public RadarCategorySetRepository() {
        super(RadarCategorySet.class);
    }

    public List<RadarCategorySet> mapList(List<RadarCategorySetEntity> source)
    {
        List<RadarCategorySet> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(int i = 0; i < source.size(); i++)
            {
                RadarCategorySet newItem = this.modelMapper.map(source.get(i), RadarCategorySet.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected RadarCategorySetEntity findOne(RadarCategorySet domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<RadarCategorySet> findByUserId(Long userId)
    {
        List<RadarCategorySet> retVal = new ArrayList<>();

        List<RadarCategorySetEntity> foundItems = this.entityRepository.findByRadarUserId(userId);

        if(foundItems!=null)
        {
            retVal = this.mapList(foundItems);
        }

        return retVal;
    }

    @Override
    public RadarCategorySet save(RadarCategorySet itemToSave)
    {
        RadarCategorySetEntity radarCategorySetEntity = null;

        if(itemToSave !=null)
        {
            if (itemToSave.getId()!=null)
            {
                radarCategorySetEntity = this.entityRepository.findOne(itemToSave.getId());
            }

            if (radarCategorySetEntity == null)
            {
                radarCategorySetEntity = new RadarCategorySetEntity();
                radarCategorySetEntity.setRadarCategories(new ArrayList<>());
            }

            radarCategorySetEntity.setName(itemToSave.getName());
            radarCategorySetEntity.setDescription(itemToSave.getDescription());
            radarCategorySetEntity.setRadarUser(this.radarUserDAO.findOne(itemToSave.getRadarUser().getId()));

            if (radarCategorySetEntity.getRadarCategories() == null)
            {
                radarCategorySetEntity.setRadarCategories(new ArrayList<RadarCategoryEntity>());
            }

            if (itemToSave.getRadarCategories() != null)
            {
                for (int i = 0; i < itemToSave.getRadarCategories().size(); i++)
                {
                    RadarCategory currentRadarCategory = itemToSave.getRadarCategories().get(i);

                    RadarCategoryEntity targetItem = radarCategorySetEntity.getRadarCategories()
                            .stream()
                            .filter(radarCategoryElement -> currentRadarCategory.getId().equals(radarCategoryElement.getId())).findFirst().orElse(null);

                    if (targetItem == null)
                    {
                        targetItem = new RadarCategoryEntity();
                    }

                    targetItem.setName(currentRadarCategory.getName());
                    targetItem.setColor(currentRadarCategory.getColor());
                    targetItem.setRadarCategorySet(radarCategorySetEntity);
                }
            }

            radarCategorySetEntity = this.entityRepository.save(radarCategorySetEntity);
        }

        return this.modelMapper.map(radarCategorySetEntity, RadarCategorySet.class);
    }
}

