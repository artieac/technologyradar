package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.dao.RadarRingDAO;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.RadarRing;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarRingRepository extends SimpleDomainRepository<RadarRing, RadarRingEntity, RadarRingDAO, Long> {
    private final EntityManager entityManager;

    @Autowired
    public RadarRingRepository(RadarMapper modelMapper,
                               RadarRingDAO radarRingDAO,
                               EntityManager entityManager){
        super(modelMapper, radarRingDAO, RadarRing.class);
        this.entityManager = entityManager;
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

