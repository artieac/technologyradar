package com.pucksandprogramming.technologyradar.data.repositories;


import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RadarRingSetRepository extends SimpleDomainRepository<RadarRingSet, RadarRingSetEntity, RadarRingSetDAO, Long>
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    public void setEntityRepository(RadarRingSetDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public RadarRingSetRepository() {
        super(RadarRingSet.class);
    }

    public List<RadarRingSet> mapList(List<RadarRingSetEntity> source)
    {
        List<RadarRingSet> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(int i = 0; i < source.size(); i++)
            {
                RadarRingSet newItem = this.modelMapper.map(source.get(i), RadarRingSet.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected RadarRingSetEntity findOne(RadarRingSet domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<RadarRingSet> findByUserId(Long userId)
    {
        List<RadarRingSet> retVal = new ArrayList<>();

        List<RadarRingSetEntity> foundItems = this.entityRepository.findByRadarUserId(userId);

        if(foundItems!=null)
        {
            retVal = this.mapList(foundItems);
        }

        return retVal;
    }

    @Override
    public RadarRingSet save(RadarRingSet itemToSave)
    {
        RadarRingSetEntity radarRingSetEntity = null;

        if(itemToSave !=null)
        {
            if (itemToSave.getId()!=null)
            {
                radarRingSetEntity = this.entityRepository.findOne(itemToSave.getId());
            }

            if (radarRingSetEntity == null)
            {
                radarRingSetEntity = new RadarRingSetEntity();
                radarRingSetEntity.setRadarRings(new ArrayList<>());
            }

            radarRingSetEntity.setName(itemToSave.getName());
            radarRingSetEntity.setDescription(itemToSave.getDescription());
            radarRingSetEntity.setRadarUser(this.radarUserDAO.findOne(itemToSave.getRadarUser().getId()));

            if (radarRingSetEntity.getRadarRings() == null)
            {
                radarRingSetEntity.setRadarRings(new ArrayList<RadarRingEntity>());
            }

            if (itemToSave.getRadarRings() != null)
            {
                for (int i = 0; i < itemToSave.getRadarRings().size(); i++)
                {
                    RadarRing currentRadarRing = itemToSave.getRadarRings().get(i);

                    RadarRingEntity targetItem = radarRingSetEntity.getRadarRings()
                            .stream()
                            .filter(radarRingElement -> currentRadarRing.getId().equals(radarRingElement.getId())).findFirst().orElse(null);

                    if (targetItem == null)
                    {
                        targetItem = new RadarRingEntity();
                    }

                    targetItem.setName(currentRadarRing.getName());
                    targetItem.setDisplayOrder(currentRadarRing.getDisplayOrder());
                    targetItem.setRadarRingSet(radarRingSetEntity);
                }
            }

            radarRingSetEntity = this.entityRepository.save(radarRingSetEntity);
        }

        return this.modelMapper.map(radarRingSetEntity, RadarRingSet.class);
    }
}
