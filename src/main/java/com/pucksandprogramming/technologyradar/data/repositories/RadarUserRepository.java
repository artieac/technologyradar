package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 12/23/2017.
 */
@Repository
public class RadarUserRepository extends SimpleDomainRepository<RadarUser, RadarUserEntity, RadarUserDAO, Long> {
    private final EntityManager entityManager;
    private final RadarTemplateDAO radarTemplateDAO;
    private final RadarRingDAO radarRingDAO;
    private final RadarCategoryDAO radarCategoryDAO;
    private final UserTypeDAO userTypeDAO;

    @Autowired
    public RadarUserRepository(RadarMapper modelMapper,
                               RadarUserDAO radarUserDAO,
                               EntityManager entityManager,
                               RadarTemplateDAO radarTemplateDAO,
                               RadarRingDAO radarRingDAO,
                               RadarCategoryDAO radarCategoryDAO,
                               UserTypeDAO userTypeDAO) {
        super(modelMapper, radarUserDAO, RadarUser.class);
        this.entityManager = entityManager;
        this.radarTemplateDAO = radarTemplateDAO;
        this.radarRingDAO = radarRingDAO;
        this.radarCategoryDAO = radarCategoryDAO;
        this.userTypeDAO = userTypeDAO;
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

    public Optional<RadarUser> findByAuthenticationId(String authenticationId) {
        RadarUserEntity foundItem = this.entityRepository.findByAuthenticationId(authenticationId);

        if (foundItem != null) {
            return Optional.ofNullable(this.modelMapper.map(foundItem, RadarUser.class));
        }

        return Optional.empty();
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