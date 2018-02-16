package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.RadarCategoryDAO;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarCategoryEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarCategoryRepository extends SimpleDomainRepository<RadarCategory, RadarCategoryEntity, RadarCategoryDAO, Long>
{
    @Autowired
    public void setEntityRepository(RadarCategoryDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarCategoryRepository()
    {
        super(RadarCategory.class);
    }

    public List<RadarCategory> findAll()
    {
        List<RadarCategory> retVal = new ArrayList<RadarCategory>();

        Iterable<RadarCategoryEntity> radarCategories = this.entityRepository.findAll();

        for (RadarCategoryEntity radarCategory : radarCategories)
        {
            retVal.add(this.modelMapper.map(radarCategory, RadarCategory.class));
        }

        return retVal;
    }

    @Override
    protected RadarCategoryEntity findOne(RadarCategory domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }
}
