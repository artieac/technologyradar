package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingSetEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTemplateEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarCategorySetDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarRingSetDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarTemplateDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRingSet;
import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RadarTemplateRepository extends SimpleDomainRepository<RadarTemplate, RadarTemplateEntity, RadarTemplateDAO, Long>
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    RadarRingSetDAO radarRingSetDAO;

    @Autowired
    RadarCategorySetDAO radarCategorySetDAO;

    @Autowired
    public void setEntityRepository(RadarTemplateDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public RadarTemplateRepository() {
        super(RadarTemplate.class);
    }

    public List<RadarTemplate> mapList(List<RadarTemplateEntity> source)
    {
        List<RadarTemplate> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(int i = 0; i < source.size(); i++)
            {
                RadarTemplate newItem = this.modelMapper.map(source.get(i), RadarTemplate.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected RadarTemplateEntity findOne(RadarTemplate domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<RadarTemplate> findByUserId(Long userId)
    {
        List<RadarTemplate> retVal = new ArrayList<>();

        List<RadarTemplateEntity> foundItems = this.entityRepository.findByRadarUserId(userId);

        if(foundItems!=null)
        {
            retVal = this.mapList(foundItems);
        }

        return retVal;
    }

    @Override
    public RadarTemplate save(RadarTemplate itemToSave)
    {
        RadarTemplateEntity saveEntity = null;

        if(itemToSave !=null)
        {
            if (itemToSave.getId()!=null)
            {
                saveEntity = this.entityRepository.findOne(itemToSave.getId());
            }

            if (saveEntity == null)
            {
                saveEntity = new RadarTemplateEntity();
            }

            saveEntity.setName(itemToSave.getName());
            saveEntity.setDescription(itemToSave.getDescription());
            saveEntity.setRadarUser(this.radarUserDAO.findOne(itemToSave.getRadarUser().getId()));
            saveEntity.setRadarCategorySet(this.radarCategorySetDAO.findOne(itemToSave.getRadarCategorySet().getId()));
            saveEntity.setRadarRingSet(this.radarRingSetDAO.findOne(itemToSave.getRadarRingSet().getId()));

            saveEntity = this.entityRepository.save(saveEntity);
        }

        return this.modelMapper.map(saveEntity, RadarTemplate.class);
    }
}
