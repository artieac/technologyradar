package com.pucksandprogramming.technologyradar.services.RadarTemplate;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTemplateRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
public class RadarTemplateService extends ServiceBase {
    private final RadarTemplateRepository radarTemplateRepository;
    private final FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarTemplateService(RadarUserRepository radarUserRepository, RadarTemplateRepository radarTemplateRepository, FullRadarRepository fullRadarRepository) {
        super(radarUserRepository);

        this.radarTemplateRepository = radarTemplateRepository;
        this.fullRadarRepository = fullRadarRepository;
    }

    public Optional<RadarTemplate> findOneShared(Long radarTemplateId) {
        Optional<RadarTemplate> radarTemplate = this.radarTemplateRepository.findById(radarTemplateId);

        if(radarTemplate.isPresent() && radarTemplate.get().getIsPublished()==false){
            return radarTemplate;
        }

        return Optional.empty();
    }

    public Optional<RadarTemplate> findOne(Long radarTemplateId) {
        if(this.getAuthenticatedUser().isPresent()) {
            return this.radarTemplateRepository.findById(radarTemplateId);
        }

        return Optional.empty();
    }

    public List<RadarTemplate> findByUserId(Long userId) {
        return this.radarTemplateRepository.findByUser(userId);
    }

    public List<RadarTemplate> findByUserAndRadarTemplate(Long userId, String radarTemplateId) {
        return this.radarTemplateRepository.findByUserAndRadarTemplate(userId, radarTemplateId);
    }

    public List<RadarTemplate> findSharedRadarTemplates(Long userToExclude) {
        List<RadarTemplate> retVal = new ArrayList<>();

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userToExclude);

        if(!dataOwner.isPresent()) {
            retVal = this.radarTemplateRepository.findSharedRadarTemplates();
        }
        else {
            retVal = this.radarTemplateRepository.findSharedRadarTemplatesExcludeOwned(userToExclude);
        }

        return retVal;
    }

    public List<RadarTemplate> findByPublishedRadars(Long excludeUserId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        Optional<RadarUser> excludeUser = this.getRadarUserRepository().findById(excludeUserId);

        if(!excludeUser.isPresent()) {
            retVal = this.radarTemplateRepository.findByPublishedRadars();
        }
        else {
            retVal = this.radarTemplateRepository.findByPublishedRadarsExcludeUser(excludeUserId);
        }

        return retVal;
    }

    public List<RadarTemplate> findOwnedWithRadars(Long dataOwnerId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(dataOwnerId);

        if(!dataOwner.isPresent()) {
            retVal = this.radarTemplateRepository.findByPublishedRadars();
        }
        else {
            retVal = this.radarTemplateRepository.findOwnedWithRadars(dataOwner.get().getId());
        }

        return retVal;
    }

    public Optional<RadarTemplate> update(Optional<RadarTemplate> radarTemplateUpdates, Long ownerId) {
        Optional<RadarTemplate> retVal = Optional.empty();

        // first maek sure it can even save at ll
        if(radarTemplateUpdates.isPresent() && radarTemplateUpdates.get().getRadarRings() != null && radarTemplateUpdates.get().getRadarRings().size() > 0) {
            boolean canSave = false;

            Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(ownerId);

            if(radarTemplateUpdates.get().getId()!=null) {
                retVal = this.radarTemplateRepository.findById(radarTemplateUpdates.get().getId());
            }

            if(!retVal.isPresent()) {
                // trying to add one, make sure they have room in their max amount
                List<RadarTemplate> userRadarTemplates = this.radarTemplateRepository.findByUser(ownerId);

                if(userRadarTemplates!=null && userRadarTemplates.size() < dataOwner.get().getUserType().getGrantValue(UserRights.AllowNRadarTemplates)) {
                    canSave = true;
                }
            }
            else {
                // trying to update an existing, make sure they can version
                if (dataOwner.isPresent() &&
                        dataOwner.get().getId() == this.getAuthenticatedUser().get().getUserId()) {
                    canSave = true;
                }
            }

            if(canSave==true) {
                radarTemplateUpdates.get().setRadarUser(dataOwner.get());
                retVal = Optional.ofNullable(this.radarTemplateRepository.save(radarTemplateUpdates.get()));
            }
        }

        return retVal;
    }

    public boolean deleteRadarTemplate(Long userId, Long radarTemplateId) {
        boolean retVal = false;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);

        if(dataOwner.isPresent()) {
            Optional<RadarTemplate> foundItem = this.radarTemplateRepository.findById(radarTemplateId);

            if(foundItem.isPresent() && foundItem.get().getRadarUser().getId() == userId &&
                (this.getAuthenticatedUser().get().getUserId()==foundItem.get().getRadarUser().getId() ||
                this.getAuthenticatedUser().get().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))) {
                List<Radar> userRadars = this.fullRadarRepository.findByUserAndType(userId, radarTemplateId);

                if(userRadars.size()==0) {
                    this.radarTemplateRepository.delete(foundItem.get());
                    retVal = true;
                }
                else {
                    this.radarTemplateRepository.deleteAll((Iterable)userRadars);

                    foundItem.get().setState(RadarTemplate.State_InActive);
                    this.radarTemplateRepository.save(foundItem.get());
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
