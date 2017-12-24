package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.RadarRingDAO;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarRingEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarRingRepository extends SimpleDomainRepository<RadarRing, RadarRingEntity, RadarRingDAO, Long>
{
    @Autowired
    public void setEntityRepository(RadarRingDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarRingRepository()
    {
        super(RadarRing.class);
    }

    public List<RadarRing> GetAllOrdered()
    {
        List<RadarRing> retVal = new ArrayList<RadarRing>();

        Iterable<RadarRingEntity> foundItems = this.entityRepository.findAll(new Sort(Sort.Direction.DESC, "DisplayOrder"));

        for (RadarRingEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarRing.class));
        }

        return retVal;
    }
}

