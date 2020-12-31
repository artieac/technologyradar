package com.pucksandprogramming.technologyradar.services.RadarTemplate;

import com.pucksandprogramming.technologyradar.data.repositories.RadarTemplateRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
public class AssociatedRadarTemplateService extends ServiceBase {
    private final RadarTemplateRepository radarTemplateRepository;

    @Autowired
    public AssociatedRadarTemplateService(RadarTemplateRepository radarTemplateRepository, RadarUserRepository radarUserRepository) {
        super(radarUserRepository);

        this.radarTemplateRepository = radarTemplateRepository;
    }

    public List<RadarTemplate> findAssociatedRadarTemplates(RadarUser targetUser) {
        List<RadarTemplate> retVal = new ArrayList<>();

        if(this.getAuthenticatedUser().isPresent()) {
            if (targetUser != null) {
                if (targetUser.getId() == this.getAuthenticatedUser().get().getUserId() ||
                        this.getAuthenticatedUser().get().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
                    retVal = this.radarTemplateRepository.findAssociatedRadarTemplates(targetUser.getId());
                }
            }
        }

        return retVal;
    }

    public boolean associateRadarTemplate(Optional<RadarUser> targetUser, Long radarTemplateId, boolean shouldAssociate) {
        boolean retVal = false;

        Optional<RadarTemplate> radarTemplate = this.radarTemplateRepository.findById(radarTemplateId);

        if(radarTemplate.isPresent() && targetUser.isPresent()) {
            if(shouldAssociate==true) {
                // don't allow a user to associate their own radar
                if (radarTemplate.get().getRadarUser().getId() != targetUser.get().getId()) {
                    List<RadarTemplate> associatedRadarTemplates = this.findAssociatedRadarTemplates(targetUser.get());

                    if(associatedRadarTemplates != null && associatedRadarTemplates.size() < targetUser.get().getUserType().getGrantValue(UserRights.AllowNAssociatedRadarTemplates)) {
                        retVal = this.radarTemplateRepository.saveAssociatedRadarTemplate(targetUser.get(), radarTemplate.get());
                    }
                }
            }
            else {
                retVal = this.radarTemplateRepository.deleteAssociatedRadarTemplate(targetUser.get(), radarTemplate.get());
            }
        }

        return retVal;
    }

    public boolean associateRadarTemplate(Long radarTemplateId, boolean shouldAssociate) {
        return this.associateRadarTemplate(this.getCurrentUser(), radarTemplateId, shouldAssociate);
    }
}
