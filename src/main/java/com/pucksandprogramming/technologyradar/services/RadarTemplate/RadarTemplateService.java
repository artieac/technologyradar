package com.pucksandprogramming.technologyradar.services.RadarTemplate;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTemplateRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RadarTemplateService extends ServiceBase {
    protected RadarTemplateRepository radarTemplateRepository;
    protected FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarTemplateService(RadarUserRepository radarUserRepository, RadarTemplateRepository radarTemplateRepository, FullRadarRepository fullRadarRepository) {
        super(radarUserRepository);

        this.radarTemplateRepository = radarTemplateRepository;
        this.fullRadarRepository = fullRadarRepository;
    }

    public RadarTemplate findOneShared(Long radarTemplateId) {
        RadarTemplate retVal = null;

        RadarUser currentUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
        retVal = this.radarTemplateRepository.findOne(radarTemplateId);

        if(retVal.getIsPublished()==false) {
            return retVal;
        }

        return retVal;
    }

    public RadarTemplate findOne(Long radarTemplateId) {
        RadarTemplate retVal = null;

        if(this.getAuthenticatedUser()!=null) {
            RadarUser currentUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
            retVal = this.radarTemplateRepository.findOne(radarTemplateId);
        }

        return retVal;
    }

    public List<RadarTemplate> findByUserId(Long userId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTemplateRepository.findByUser(userId);

        return retVal;
    }

    public List<RadarTemplate> findByUserAndRadarTemplate(Long userId, String radarTemplateId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTemplateRepository.findByUserAndRadarTemplate(userId, radarTemplateId);

        return retVal;
    }

    public List<RadarTemplate> findSharedRadarTemplates(Long userToExclude) {
        List<RadarTemplate> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userToExclude);

        if(dataOwner==null) {
            retVal = this.radarTemplateRepository.findSharedRadarTemplates();
        }
        else {
            retVal = this.radarTemplateRepository.findSharedRadarTemplatesExcludeOwned(userToExclude);
        }

        return retVal;
    }

    public List<RadarTemplate> findByPublishedRadars(Long excludeUserId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        RadarUser excludeUser = this.getRadarUserRepository().findOne(excludeUserId);

        if(excludeUser==null) {
            retVal = this.radarTemplateRepository.findByPublishedRadars();
        }
        else {
            retVal = this.radarTemplateRepository.findByPublishedRadarsExcludeUser(excludeUserId);
        }

        return retVal;
    }

    public List<RadarTemplate> findOwnedWithRadars(Long dataOwnerId) {
        List<RadarTemplate> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(dataOwnerId);

        if(dataOwner==null) {
            retVal = this.radarTemplateRepository.findByPublishedRadars();
        }
        else {
            retVal = this.radarTemplateRepository.findOwnedWithRadars(dataOwner.getId());
        }

        return retVal;
    }

    public RadarTemplate update(RadarTemplate radarTemplateUpdates, Long ownerId) {
        RadarTemplate retVal = null;

        // first maek sure it can even save at ll
        if(radarTemplateUpdates != null && radarTemplateUpdates.getRadarRings() != null && radarTemplateUpdates.getRadarRings().size() > 0) {
            boolean canSave = false;

            RadarUser dataOwner = this.getRadarUserRepository().findOne(ownerId);

            if(radarTemplateUpdates.getId()!=null) {
                retVal = this.radarTemplateRepository.findOne(radarTemplateUpdates.getId());
            }

            if(retVal==null) {
                // trying to add one, make sure they have room in their max amount
                List<RadarTemplate> userRadarTemplates = this.radarTemplateRepository.findByUser(ownerId);

                if(userRadarTemplates!=null && userRadarTemplates.size() < dataOwner.getUserType().getGrantValue(UserRights.AllowNRadarTemplates)) {
                    canSave = true;
                }
            }
            else {
                // trying to update an existing, make sure they can version
                if (dataOwner != null &&
                        dataOwner.getId() == this.getAuthenticatedUser().getUserId()) {
                    canSave = true;
                }
            }

            if(canSave==true) {
                radarTemplateUpdates.setRadarUser(dataOwner);
                retVal = this.radarTemplateRepository.save(radarTemplateUpdates);
            }
        }

        return retVal;
    }

    public boolean deleteRadarTemplate(Long userId, Long radarTemplateId) {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null) {
            RadarTemplate foundItem = this.radarTemplateRepository.findOne(radarTemplateId);

            if(foundItem.getRadarUser().getId() == userId &&
                (this.getAuthenticatedUser().getUserId()==foundItem.getRadarUser().getId() ||
                this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))) {
                List<Radar> userRadars = this.fullRadarRepository.findByUserAndType(userId, radarTemplateId);

                if(userRadars.size()==0) {
                    this.radarTemplateRepository.delete(foundItem);
                    retVal = true;
                }
                else {
                    this.radarTemplateRepository.delete((Iterable)userRadars);

                    foundItem.setState(RadarTemplate.State_InActive);
                    this.radarTemplateRepository.save(foundItem);
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
