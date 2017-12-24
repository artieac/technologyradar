package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.Entities.RadarUserEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.data.dao.RadarUserDAO;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 12/23/2017.
 */
@Repository
public class RadarUserRepository extends SimpleDomainRepository<RadarUser, RadarUserEntity, RadarUserDAO, Long> {
    @Autowired
    EntityManager entityManager;

    @Autowired
    public void setEntityRepository(RadarUserDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public RadarUserRepository() {
        super(RadarUser.class);
    }

    private List<RadarUser> mapList(List<RadarUserEntity> source) {
        List<RadarUser> retVal = new ArrayList<>();

        if (source != null) {
            for (int i = 0; i < source.size(); i++) {
                RadarUser newItem = this.modelMapper.map(source.get(i), RadarUser.class);
                retVal.add(newItem);
            }
        }

        return retVal;

    }

    public RadarUser findByAuthenticationId(String authenticationId) {
        RadarUser retVal = null;

        RadarUserEntity foundItem = this.entityRepository.findByAuthenticationId(authenticationId);

        if (foundItem != null) {
            retVal = this.modelMapper.map(foundItem, RadarUser.class);
        }

        return retVal;
    }

    @Override
    public RadarUser save(RadarUser radarUser)
    {
        RadarUserEntity itemToSave = this.modelMapper.map(radarUser, RadarUserEntity.class);

        if(itemToSave != null)
        {
            this.entityRepository.save(itemToSave);
        }

        return null;
    }
}