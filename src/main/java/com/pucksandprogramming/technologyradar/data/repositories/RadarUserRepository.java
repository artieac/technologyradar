package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategoryEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarCategoryDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarRingDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarTypeDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.data.mapper.TechnologyRadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.*;
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
    RadarTypeDAO radarTypeDAO;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

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
    protected RadarUserEntity findOne(RadarUser domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
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
    public RadarUser save(RadarUser itemToSave)
    {
        RadarUserEntity radarUserEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null) {
            if (itemToSave != null && itemToSave.getId() != null) {
                radarUserEntity = this.entityRepository.findOne(itemToSave.getId());
            } else {
                radarUserEntity = new RadarUserEntity();
            }

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

                // process Radar Types
                // First remove any deletions
                if (radarUserEntity != null && radarUserEntity.getRadarTypes() != null) {
                    for (RadarTypeEntity radarTypeEntity : radarUserEntity.getRadarTypes()) {
                        boolean foundMatch = false;

                        for (RadarType radarType : itemToSave.getRadarTypes()) {
                            if (radarType.getId() == radarTypeEntity.getId()) {
                                foundMatch = true;

                                // copy over
                                radarTypeEntity.setName(radarType.getName());

                                // radar rings
                                for(RadarRing radarRing : radarType.getRadarRings()) {
                                    for (RadarRingEntity copyTarget : radarTypeEntity.getRadarRings()) {
                                        if (copyTarget.getId() == radarRing.getId()) {
                                            copyTarget.setName(radarRing.getName());
                                            copyTarget.setDisplayOrder(radarRing.getDisplayOrder());
                                            break;
                                        }
                                    }
                                }

                                for(RadarCategory radarCategory : radarType.getRadarCategories()){
                                    for(RadarCategoryEntity copyTarget : radarTypeEntity.getRadarCategories()){
                                        if(copyTarget.getId()==radarCategory.getId()){
                                            copyTarget.setName(radarCategory.getName());
                                            copyTarget.setColor(radarCategory.getColor());
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }

                        if (foundMatch == false) {
                            radarUserEntity.getRadarTypes().remove(radarTypeEntity);
                            radarTypeDAO.delete(radarTypeEntity);
                        }
                    }
                }

                // then add in any new ones
                for (RadarType radarType : itemToSave.getRadarTypes()) {
                    boolean foundMatch = false;

                    for (RadarTypeEntity radarTypeEntity : radarUserEntity.getRadarTypes()) {
                        if (radarTypeEntity.getId() == radarType.getId()) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch == false) {
                        RadarTypeEntity newItem = new RadarTypeEntity();
                        newItem.setName(radarType.getName());
                        newItem.setName(radarType.getName());

                        // radar rings
                        newItem.setRadarRings(new ArrayList<RadarRingEntity>());

                        for(RadarRing radarRing : radarType.getRadarRings())
                        {
                            RadarRingEntity newRingItem = new RadarRingEntity();
                            newRingItem.setName(radarRing.getName());
                            newRingItem.setDisplayOrder(radarRing.getDisplayOrder());
                            radarRingDAO.save(newRingItem);
                            newItem.getRadarRings().add(newRingItem);
                        }

                        // radar categories
                        newItem.setRadarCategories(new ArrayList<RadarCategoryEntity>());

                        for(RadarCategory radarCategory : radarType.getRadarCategories())
                        {
                            RadarCategoryEntity newCategoryItem = new RadarCategoryEntity();
                            newCategoryItem.setName(radarCategory.getName());
                            newCategoryItem.setColor(radarCategory.getColor());
                            radarCategoryDAO.save(newCategoryItem);
                            newItem.getRadarCategories().add(newCategoryItem);
                        }

                        radarUserEntity.getRadarTypes().add(newItem);
                    }
                }
            }

            if (radarUserEntity != null) {
                this.entityRepository.save(radarUserEntity);
            }
        }

        return this.modelMapper.map(this.findOne(itemToSave.getId()), RadarUser.class);
    }
}