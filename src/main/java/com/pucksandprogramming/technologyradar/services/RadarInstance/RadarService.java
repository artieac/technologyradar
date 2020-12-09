package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarItemToBeAdded;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/21/2016.
 */

@Component
@RequestScope
public class RadarService extends ServiceBase {
    protected final RadarRepositoryFactory radarRepositoryFactory;
    protected final TechnologyRepository technologyRepository;
    protected final RadarRingRepository radarRingRepository;
    protected final RadarCategoryRepository radarCategoryRepository;
    protected final RadarAccessManager radarAccessManager;
    protected final RadarTemplateRepository radarTemplateRepository;

    public RadarService(RadarRepositoryFactory radarRepositoryFactory,
                        RadarTemplateRepository radarTemplateRepository,
                        TechnologyRepository technologyRepository,
                        RadarRingRepository radarRingRepository,
                        RadarCategoryRepository radarCategoryRepository,
                        RadarUserRepository radarUserRepository,
                        RadarAccessManager radarAccessManager) {
        super(radarUserRepository);

        this.radarRepositoryFactory = radarRepositoryFactory;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarAccessManager = radarAccessManager;
        this.radarTemplateRepository = radarTemplateRepository;
    }

    public Radar createDefault(RadarUser radarUser) {
        Radar retVal = new Radar();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setRadarUser(radarUser);
        retVal.setRadarItems(new ArrayList<RadarItem>());
        return retVal;
    }

    public Optional<Radar> findById(Long radarId) {
        return this.radarRepositoryFactory.getRadarRepository().findById(radarId);
    }

    public List<Radar> findByRadarUserId(Long radarUserId) {
        List<Radar> retVal = new ArrayList<>();
        Optional<RadarUser> targetDataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(targetDataOwner.isPresent()) {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserId(targetDataOwner.get().getId());
        }

        return retVal;
    }

    public Radar findByUserAndRadarId(Long radarUserId, Long radarId) {
        Radar retVal = null;
        Optional<RadarUser> targetDataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(targetDataOwner.isPresent()) {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserRadarId(targetDataOwner.get().getId(), radarId).get();
        }

        return retVal;
    }

    public List<Radar> findByUserAndType(Long radarUserId, Long radarTemplateId) {
        List<Radar> retVal = new ArrayList<>();
        Optional<RadarUser> targetDataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(targetDataOwner.isPresent()) {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserAndType(targetDataOwner.get().getId(), radarTemplateId);
        }

        return retVal;
    }

    public List<Radar> findByUserTypeAndVersion(Long radarUserId, Long radarTemplateId) {
        List<Radar> retVal = new ArrayList<>();
        Optional<RadarUser> targetDataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(targetDataOwner.isPresent()) {
            retVal = this.radarRepositoryFactory.getRadarRepository(targetDataOwner).findByUserAndType(targetDataOwner.get().getId(), radarTemplateId);
        }

        return retVal;
    }

    public List<Radar> getAllByRadarSubjectId(Long radarSubjectId) {
        return this.radarRepositoryFactory.getRadarRepository().findByRadarSubjectId(radarSubjectId);
    }

    public List<Radar> getAllNotOwnedByTechnologyId(Long radarUserId, Long technologyId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Optional<Technology> foundItem = this.technologyRepository.findById(technologyId);
        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(foundItem.isPresent()) {
            if (dataOwner.isPresent()) {
                retVal = this.radarRepositoryFactory.getRadarRepository().findNotOwnedByRadarSubjectAndUser(dataOwner.get().getId(), foundItem.get().getId());
            }
            else {
                retVal = this.radarRepositoryFactory.getRadarRepository().findByRadarSubjectId(foundItem.get().getId());
            }
        }

        return retVal;
    }

    public List<Radar> getAllOwnedByTechnologyId(Long radarUserId, Long technologyId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Optional<Technology> foundItem = this.technologyRepository.findById(technologyId);
        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

        if(foundItem.isPresent()) {
            retVal = this.radarRepositoryFactory.getRadarRepository(dataOwner).findOwnedByTechnologyId(dataOwner.get().getId(), foundItem.get().getId());
        }

        return retVal;
    }

    public Integer getSharedRadarCount(Long radarUserId) {
        Integer retVal = 0;

        if(this.getAuthenticatedUser()!=null) {
            Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

            List<Radar> publishedRadars = this.radarRepositoryFactory.getRadarRepository(dataOwner).findAllPublishedRadarsByUser(this.getAuthenticatedUser().getUserId());

            if(publishedRadars!=null) {
                retVal = publishedRadars.size();
            }
        }

        return retVal;
    }

    public Radar findCurrentByType(Long radarUserId, Long radarTemplateId) {
        Radar retVal = null;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);
        Optional<RadarTemplate> radarTemplate = this.radarTemplateRepository.findById(radarTemplateId);

        if(dataOwner.isPresent() && radarTemplate.isPresent()) {
            List<RadarItem> foundItems = this.radarRepositoryFactory.getRadarRepository(dataOwner).findCurrentByType(radarUserId, radarTemplateId);

            retVal = new Radar();
            retVal.setAssessmentDate(new Date());
            retVal.setRadarTemplate(radarTemplate.get());
            retVal.setIsLocked(true);
            retVal.setIsPublished(true);
            retVal.setName("Current");
            retVal.setRadarItems(foundItems);
            retVal.setRadarUser(dataOwner.get());
            retVal.setId(-1L);
        }

        return retVal;
    }

    public Radar addRadar(Long radarUserId, String name, Long radarTemplateId) {
        Radar retVal = null;

        if(!name.isEmpty()) {
            Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

            if(this.radarAccessManager.canModifyRadar(dataOwner)) {
                RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

                Optional<RadarTemplate> radarTemplate = this.radarTemplateRepository.findById(radarTemplateId);

                if (dataOwner.isPresent() && radarTemplate.isPresent()) {
                    retVal = this.createDefault(dataOwner.get());
                    retVal.setName(name);
                    retVal.setRadarTemplate(radarTemplate.get());
                    radarRepository.save(retVal);
                }
            }
        }

        return retVal;
    }

    public boolean deleteRadar(Long radarUserId, Long radarInstanceId) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Optional<Radar> radar = radarRepository.findByUserRadarId(radarUserId, radarInstanceId);

        if(radar.isPresent() && radar.get().getIsPublished()==false && radar.get().getIsLocked()==false) {
            if(this.radarAccessManager.canModifyRadar(dataOwner)) {
                radarRepository.deleteById(radar.get().getId());
                retVal = true;
            }
        }

        return retVal;
    }

    public Radar updateRadar(Long radarUserId, Long radarId, String name) {
        Radar retVal = null;

        if(!name.isEmpty()) {
            Optional<Radar> targetRadar = this.findById(radarId);
            Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

            if(this.radarAccessManager.canModifyRadar(dataOwner)) {
                if (targetRadar.isPresent() && retVal.getIsLocked() == false) {
                    targetRadar.get().setName(name);
                    this.radarRepositoryFactory.getRadarRepository(dataOwner).save(targetRadar.get());
                    retVal = targetRadar.get();
                }
            }
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, String technologyName, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails) {
        Radar retVal = null;

        if(this.radarAccessManager.canModifyRadar(radarUser)) {
            if (!technologyName.isEmpty()) {
                Optional<Technology> targetTechnology = this.technologyRepository.findByName(technologyName);

                if (!targetTechnology.isPresent()) {
                    Technology newTechnology = new Technology();
                    newTechnology.setName(technologyName);
                    newTechnology.setCreateDate(new Date());
                    newTechnology.setUrl(technologyUrl);
                    newTechnology.setCreator(radarUser.getId().toString());
                    targetTechnology = Optional.of(newTechnology);
                }

                retVal = this.addRadarItem(radarUser, radarId, targetTechnology.get(), radarCategoryId, radarRingId, confidenceLevel, assessmentDetails);
            }
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails) {
        Optional<Radar> retVal = Optional.ofNullable(null);

        if(this.radarAccessManager.canModifyRadar(radarUser)) {
            if (targetTechnology != null) {
                retVal = this.findById(radarId);

                if (retVal.isPresent() && retVal.get().getIsLocked() == false && radarUser != null && retVal.get().getRadarUser().getId() == radarUser.getId()) {
                    Optional<RadarRing> radarRing = this.radarRingRepository.findById(radarRingId);
                    Optional<RadarCategory> radarCategory = this.radarCategoryRepository.findById(radarCategoryId);

                    if (retVal.get().getRadarTemplate().hasRadarRing(radarRing.get()) && retVal.get().getRadarTemplate().hasRadarCategory(radarCategory.get())) {
                        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(Optional.ofNullable(radarUser));

                        Integer itemState = RadarItem.State_New;

                        RadarItem radarItemToAdd = new RadarItem(-1L, targetTechnology, radarCategory.get(), radarRing.get(), confidenceLevel, assessmentDetails);
                        Optional<RadarItem> previousRadarItem = radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(retVal.get().getRadarUser().getId(), retVal.get().getId(), targetTechnology.getId());

                        radarItemToAdd.determineState(previousRadarItem);
                        retVal.get().addRadarItem(radarItemToAdd);

                        radarRepository.save(retVal.get());
                    }
                }
            }
        }

        return retVal.get();
    }

    public Radar addRadarItems(RadarUser radarUser, Long radarId, List<RadarItemToBeAdded> radarItems) {
        Optional<Radar> retVal = this.findById(radarId);

        if(this.radarAccessManager.canModifyRadar(radarUser)) {
            if (radarItems != null) {
                if (retVal.isPresent() && retVal.get().getIsLocked() == false && radarUser != null && retVal.get().getRadarUser().getId() == radarUser.getId()) {
                    for (int i = 0; i < radarItems.size(); i++) {
                        RadarItemToBeAdded currentItemToAdd = radarItems.get(i);
                        Optional<RadarRing> radarRing = this.radarRingRepository.findById(currentItemToAdd.getRadarRingId());
                        Optional<RadarCategory> radarCategory = this.radarCategoryRepository.findById(currentItemToAdd.getRadarCategoryId());
                        Optional<Technology> targetTechnology = this.technologyRepository.findById(currentItemToAdd.getTechnologyId());

                        RadarItem newRadarItem = new RadarItem(-1L, targetTechnology.get(), radarCategory.get(), radarRing.get(), currentItemToAdd.getConfidenceFactor(), currentItemToAdd.getDetails());

                        retVal.get().addRadarItem(newRadarItem);
                    }

                    this.radarRepositoryFactory.getRadarRepository(Optional.ofNullable(radarUser)).save(retVal.get());
                }
            }
        }

        return retVal.get();
    }

    public RadarItem updateRadarItem(Long radarId, Long radarItemId, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails) {
        Optional<RadarItem> retVal = Optional.ofNullable(null);

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0) {
            Optional<Radar> radar = this.findById(radarId);

            if(radar.isPresent() && radar.get().getIsLocked() == false) {
                if(this.radarAccessManager.canModifyRadar(radar.get().getRadarUser())) {
                    RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(Optional.ofNullable(radar.get().getRadarUser()));

                    RadarItem radarItemToUpdate = radar.get().findRadarItemById(radarItemId);
                    radarItemToUpdate.setId(radarItemId);
                    radarItemToUpdate.setRadarRing(this.radarRingRepository.findById(radarRingId).get());
                    radarItemToUpdate.setRadarCategory(this.radarCategoryRepository.findById(radarCategoryId).get());
                    radarItemToUpdate.setConfidenceFactor(confidenceLevel);
                    radarItemToUpdate.setDetails(assessmentDetails);

                    Optional<RadarItem> previousRadarItem = radarRepository.getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(radar.get().getRadarUser().getId(), radar.get().getId(), radarItemToUpdate.getTechnology().getId());
                    radarItemToUpdate.determineState(previousRadarItem);

                    radar.get().updateRadarItem(radarItemId, radarItemToUpdate);
                    radarRepository.save(radar.get());
                }
            }
        }
        return retVal.get();
    }

    public boolean deleteRadarItem(Long radarId, Long radarItemId, Long radarUserId) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(radarUserId);

        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Optional<Radar> radar = radarRepository.findByUserRadarId(radarUserId, radarId);

        if(radar.isPresent() && radar.get().getIsLocked() == false) {
            if(this.radarAccessManager.canModifyRadar(radar.get().getRadarUser())) {
                radar.get().removeRadarItem(radarItemId);
                radarRepository.save(radar.get());
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean deleteRadarItems(Long userId, Long radarId, List<Long> radarItemIds) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);
        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Optional<Radar> radar = radarRepository.findByUserRadarId(userId, radarId);

        if(radar.isPresent() && radar.get().getIsLocked() == false) {
            if(this.radarAccessManager.canModifyRadar(radar.get().getRadarUser())) {
                for (int i = 0; i < radarItemIds.size(); i++) {
                    radar.get().removeRadarItem(radarItemIds.get(i));
                }

                radarRepository.save(radar.get());
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean publishRadar(Long userId, Long radarId, boolean shouldPublish) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);

        if(this.radarAccessManager.canModifyRadar(dataOwner)) {
            RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

            Optional<Radar> radar = radarRepository.findByUserRadarId(userId, radarId);

            if (radar.isPresent() && radar.get().getIsLocked() == false && radar.get().getIsPublished() != shouldPublish) {
                List<Radar> currentPublishedRadars = radarRepository.findAllPublishedRadarsByUser(dataOwner.get().getId());

                if (currentPublishedRadars.size() >= dataOwner.get().howManyRadarsCanShare())
                {
                    // unpublish the oldest and then publish the one they want.
                    currentPublishedRadars.get(0).setIsPublished(false);
                    radarRepository.save(currentPublishedRadars.get(0));
                }

                radar.get().setIsPublished(shouldPublish);
                radarRepository.save(radar.get());
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean lockRadar(Long userId, Long radarId, boolean shouldLock) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);
        RadarRepositoryBase radarRepository = this.radarRepositoryFactory.getRadarRepository(dataOwner);

        Optional<Radar> radar = radarRepository.findByUserRadarId(userId, radarId);

        if(radar.isPresent()) {
            if (this.radarAccessManager.canModifyRadar(radar.get().getRadarUser())) {
                radar.get().setIsLocked(shouldLock);
                radarRepository.save(radar.get());
                retVal = true;
            }
        }

        return retVal;
    }
}
