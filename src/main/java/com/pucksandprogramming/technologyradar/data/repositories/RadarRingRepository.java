package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.dao.RadarRingDAO;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarRingRepository extends SimpleDomainRepository<RadarRing, RadarRingEntity, RadarRingDAO, Long> {
    @Autowired
    EntityManager entityManager;

    @Autowired
    public void setEntityRepository(RadarRingDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarRingRepository()
    {
        super(RadarRing.class);
    }

    @Override
    protected Optional<RadarRingEntity> findOne(RadarRing domainModel) {
        return this.entityRepository.findById(domainModel.getId());
    }

    public List<RadarRing> GetAllOrdered() {
        List<RadarRing> retVal = new ArrayList<RadarRing>();

        Iterable<RadarRingEntity> foundItems = this.entityRepository.findAll(Sort.by(Sort.Direction.DESC, "DisplayOrd"));

        for (RadarRingEntity foundItem : foundItems) {
            retVal.add(this.modelMapper.map(foundItem, RadarRing.class));
        }

        return retVal;
    }
}

