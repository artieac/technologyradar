package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Repository
public class RadarTemplateRepository extends SimpleDomainRepository<RadarTemplate, RadarTemplateEntity, RadarTemplateDAO, Long> {
    private final RadarRingDAO radarRingDAO;
    private final RadarCategoryDAO radarCategoryDAO;
    private final RadarUserDAO radarUserDAO;
    private final AssociatedRadarTemplateDAO associatedRadarTemplateDAO;
    private final RadarDAO radarDAO;
    private final EntityManager entityManager;

    @Autowired
    public RadarTemplateRepository(RadarMapper modelMapper,
                                    RadarTemplateDAO entityRepository,
                                    RadarRingDAO radarRingDAO,
                                    RadarCategoryDAO radarCategoryDAO,
                                    RadarUserDAO radarUserDAO,
                                    AssociatedRadarTemplateDAO associatedRadarTemplateDAO,
                                    RadarDAO radarDAO,
                                    EntityManager entityManager) {
        super(modelMapper, entityRepository, RadarTemplate.class);
        this.radarRingDAO = radarRingDAO;
        this.radarCategoryDAO = radarCategoryDAO;
        this.radarUserDAO = radarUserDAO;
        this.associatedRadarTemplateDAO = associatedRadarTemplateDAO;
        this.radarDAO = radarDAO;
        this.entityManager = entityManager;
    }

    public List<RadarTemplate> mapList(List<RadarTemplateEntity> source) {
        List<RadarTemplate> retVal = new ArrayList<>();

        if(source!=null) {
            for(int i = 0; i < source.size(); i++) {
                RadarTemplate newItem = this.modelMapper.map(source.get(i), RadarTemplate.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected Optional<RadarTemplateEntity> findOne(RadarTemplate domainModel) {
        return this.entityRepository.findById(domainModel.getId());
    }

    public List<RadarTemplate> findByUser(Long userId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        List<RadarTemplateEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndStateOrderByCreateDate(userId, 1);

        if(foundItems != null) {
            for (RadarTemplateEntity foundItem : foundItems) {
                retVal.add(this.modelMapper.map(foundItem, RadarTemplate.class));
            }
        }

        return retVal;
    }

    public List<RadarTemplate> findByUserAndRadarTemplate(Long userId, String radarTemplateId) {
        Query query = entityManager.createNamedQuery("owned_FindHistoryByRadarUserIdAndId");
        query.setParameter("radarUserId", userId);
        query.setParameter("radarTemplateId", radarTemplateId);
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarTemplate> findSharedRadarTemplatesExcludeOwned(Long radarUserId) {
        Query query = entityManager.createNamedQuery("owned_FindHistorySharedRadarTemplatesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarTemplate> findSharedRadarTemplates() {
        Query query =  this.entityManager.createNamedQuery("public_findSharedRadarTemplates");
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarTemplate> findAssociatedRadarTemplates(Long radarUserId) {
        Query query = entityManager.createNamedQuery("findAllAssociated");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }


    public List<RadarTemplate> findByPublishedRadars() {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadars");
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarTemplate> findByPublishedRadarsExcludeUser(Long excludeUserId) {
        Query query = entityManager.createNamedQuery("findAllForPublishedRadarsExcludeUser");
        query.setParameter("radarUserId", excludeUserId);
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<RadarTemplate> findOwnedWithRadars(Long radarUserId) {
        Query query = entityManager.createNamedQuery("findAllOwnedWithRadars");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTemplateEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public RadarTemplate save(RadarTemplate itemToSave) {
        Optional<RadarTemplateEntity> targetRadarTemplateEntity = null;

        if(itemToSave !=null) {
            if (itemToSave.getId() != null && itemToSave.getId() > 0) {
                targetRadarTemplateEntity = this.entityRepository.findById(itemToSave.getId());
            }
            else {
                targetRadarTemplateEntity = Optional.of(new RadarTemplateEntity());
            }

            RadarTemplateEntity radarTemplateEntity = targetRadarTemplateEntity.get();

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (radarTemplateEntity != null) {
                radarTemplateEntity.setName(itemToSave.getName());
                radarTemplateEntity.setIsPublished(itemToSave.getIsPublished());
                radarTemplateEntity.setDescription(itemToSave.getDescription());
                radarTemplateEntity.setState(itemToSave.getState());
                radarTemplateEntity.setRadarUser(radarUserDAO.findById(itemToSave.getRadarUser().getId()).get());

                // save it here so we can add the rings and categories.  Not sure how else to do this.  Doesn't feel right though.
                radarTemplateEntity = this.entityRepository.saveAndFlush(radarTemplateEntity);

                // Some ground rules.  If it has any radar's associated with it you can't remove any radar rings.
                List<RadarEntity> createdRadars = this.radarDAO.findAllByRadarUserIdAndRadarTemplateId(itemToSave.getRadarUser().getId(), itemToSave.getId());

                if(createdRadars==null || createdRadars.size()==0) {
                    // process Radar Rings
                    if (radarTemplateEntity.getRadarRings() != null) {
                        for (RadarRingEntity radarRingEntity : radarTemplateEntity.getRadarRings()) {
                            boolean foundMatch = false;

                            for (RadarRing radarRing : itemToSave.getRadarRings()) {
                                if (radarRing.getId() == radarRingEntity.getId()) {
                                    foundMatch = true;
                                    // match found, overwrite changes
                                    radarRingEntity.setName(radarRing.getName());
                                    radarRingEntity.setDisplayOrder(radarRing.getDisplayOrder());
                                }
                            }

                            // it wasn't found in both lists, delete it
                            if (foundMatch == false) {
                                radarTemplateEntity.getRadarRings().remove(radarRingEntity);
                                radarRingDAO.delete(radarRingEntity);
                            }
                        }
                    }

                    // process Radar Categories
                    // First remove any deletions
                    if(radarTemplateEntity.getRadarCategories() != null) {
                        for (RadarCategoryEntity radarCategoryEntity : radarTemplateEntity.getRadarCategories()) {
                            boolean foundMatch = false;

                            for (RadarCategory radarCategory : itemToSave.getRadarCategories()) {
                                if (radarCategory.getId() == radarCategoryEntity.getId()) {
                                    foundMatch = true;
                                    // match found, overwrite changes
                                    radarCategoryEntity.setName(radarCategory.getName());
                                    radarCategoryEntity.setColor(radarCategory.getColor());
                                }
                            }

                            // it wasn't found in both lists, delete it
                            if (foundMatch == false) {
                                radarTemplateEntity.getRadarCategories().remove(radarCategoryEntity);
                                radarCategoryDAO.delete(radarCategoryEntity);
                            }
                        }
                    }
                }

                // then add in any new ones
                for (RadarRing radarRing : itemToSave.getRadarRings()) {
                    boolean foundMatch = false;

                    if(radarTemplateEntity.getRadarRings()!=null) {
                        for (RadarRingEntity radarRingEntity : radarTemplateEntity.getRadarRings()) {
                            if (radarRingEntity.getId() == radarRing.getId()) {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else {
                        radarTemplateEntity.setRadarRings(new ArrayList<RadarRingEntity>());
                    }

                    if (foundMatch == false) {
                        RadarRingEntity newItem = new RadarRingEntity();
                        newItem.setName(radarRing.getName());
                        newItem.setDisplayOrder(radarRing.getDisplayOrder());
                        newItem.setRadarTemplate(radarTemplateEntity);
                        this.radarRingDAO.save(newItem);
                        radarTemplateEntity.getRadarRings().add(newItem);
                    }
                }

                // then add in any new ones
                for (RadarCategory radarCategory : itemToSave.getRadarCategories()) {
                    boolean foundMatch = false;

                    if(radarTemplateEntity.getRadarCategories()!=null) {
                        for (RadarCategoryEntity radarCategoryEntity : radarTemplateEntity.getRadarCategories()) {
                            if (radarCategoryEntity.getId() == radarCategory.getId()) {
                                foundMatch = true;
                                break;
                            }
                        }
                    }
                    else {
                        radarTemplateEntity.setRadarCategories(new ArrayList<RadarCategoryEntity>());
                    }

                    if (foundMatch == false) {
                        RadarCategoryEntity newItem = new RadarCategoryEntity();
                        newItem.setName(radarCategory.getName());
                        newItem.setColor(radarCategory.getColor());
                        newItem.setRadarTemplate(radarTemplateEntity);
                        this.radarCategoryDAO.save(newItem);
                        radarTemplateEntity.getRadarCategories().add(newItem);
                    }
                }
            }

            if (radarTemplateEntity != null) {
                targetRadarTemplateEntity = Optional.of(this.entityRepository.save(radarTemplateEntity));
            }
        }

        return this.modelMapper.map(targetRadarTemplateEntity.get(), RadarTemplate.class);
    }

    public boolean saveAssociatedRadarTemplate(RadarUser radarUser, RadarTemplate radarTemplate) {
        boolean retVal = false;

        if (radarTemplate != null && radarUser != null) {
            AssociatedRadarTemplateEntity associatedRadarTemplateEntityRadarTemplateEntity = this.associatedRadarTemplateDAO.findByRadarUserIdAndRadarTemplateId(radarUser.getId(), radarTemplate.getId());

            if(associatedRadarTemplateEntityRadarTemplateEntity==null) {
                associatedRadarTemplateEntityRadarTemplateEntity = new AssociatedRadarTemplateEntity();
                associatedRadarTemplateEntityRadarTemplateEntity.setRadarUserId(radarUser.getId());
                associatedRadarTemplateEntityRadarTemplateEntity.setRadarTemplateId(radarTemplate.getId());
                associatedRadarTemplateEntityRadarTemplateEntity = this.associatedRadarTemplateDAO.save(associatedRadarTemplateEntityRadarTemplateEntity);

                if(associatedRadarTemplateEntityRadarTemplateEntity.getId() > 0) {
                    retVal = true;
                }
            }
            else {
                retVal = true;
            }
        }

        return retVal;
    }

    public boolean deleteAssociatedRadarTemplate(RadarUser radarUser, RadarTemplate radarTemplate) {
        boolean retVal = false;

        if (radarTemplate != null && radarUser != null) {
            AssociatedRadarTemplateEntity radarTemplateEntity = this.associatedRadarTemplateDAO.findByRadarUserIdAndRadarTemplateId(radarUser.getId(), radarTemplate.getId());

            if (radarTemplateEntity != null) {
                this.associatedRadarTemplateDAO.deleteById(radarTemplateEntity.getId());
                retVal = true;
            }
        }

        return retVal;
    }
}

