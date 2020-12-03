package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 12/23/2017.
 */
@Repository
public class RadarUserRepository extends SimpleDomainRepository<RadarUser, RadarUserEntity, RadarUserDAO, Long> {
    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarTemplateDAO radarTemplateDAO;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    UserTypeDAO userTypeDAO;

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

    @Override
    protected Optional<RadarUserEntity> findOne(RadarUser domainModel) {
        return this.entityRepository.findById(domainModel.getId());
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
    public RadarUser save(RadarUser itemToSave) {
        Optional<RadarUserEntity> targetItem = null;

        if(itemToSave !=null && itemToSave.getId() != null) {
            if (itemToSave != null && itemToSave.getId() != null && itemToSave.getId() > 0) {
                targetItem = this.entityRepository.findById(itemToSave.getId());
            } else {
                targetItem = Optional.of(new RadarUserEntity());
            }

            RadarUserEntity radarUserEntity = targetItem.get();

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (radarUserEntity != null) {
                radarUserEntity.setAuthenticationId(itemToSave.getAuthenticationId());
                radarUserEntity.setAuthority(itemToSave.getAuthority());
                radarUserEntity.setEmail(itemToSave.getEmail());
                radarUserEntity.setIssuer(itemToSave.getIssuer());
                radarUserEntity.setName(itemToSave.getName());
                radarUserEntity.setNickname(itemToSave.getNickname());
                radarUserEntity.setRoleId(itemToSave.getRoleId());
                radarUserEntity.setUserType(this.userTypeDAO.findById(itemToSave.getUserType().getId()).get());
            }

            if (radarUserEntity != null) {
                this.entityRepository.save(radarUserEntity);
            }
        }

        return this.modelMapper.map(this.findOne(itemToSave), RadarUser.class);
    }
}