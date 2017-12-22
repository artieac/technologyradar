package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dto.RadarCategoryDTO;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import com.alwaysmoveforward.technologyradar.data.dao.RadarCategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarCategoryRepository extends SimpleDomainRepository<RadarCategory, RadarCategoryDTO, RadarCategoryDAO, Long>
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

        Iterable<RadarCategoryDTO> radarCategories = this.entityRepository.findAll();

        for (RadarCategoryDTO radarCategory : radarCategories)
        {
            retVal.add(this.modelMapper.map(radarCategory, RadarCategory.class));
        }

        return retVal;
    }
}
