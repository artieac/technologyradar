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

import java.util.ArrayList;
import java.util.List;

@Component
public class AssociatedRadarTemplateService extends ServiceBase
{
    RadarTemplateRepository radarTemplateRepository;

    @Autowired
    public AssociatedRadarTemplateService(RadarTemplateRepository radarTemplateRepository, RadarUserRepository radarUserRepository)
    {
        super(radarUserRepository);

        this.radarTemplateRepository = radarTemplateRepository;
    }

    public List<RadarTemplate> findAssociatedRadarTemplates(RadarUser targetUser)
    {
        List<RadarTemplate> retVal = new ArrayList<>();

        if(this.getAuthenticatedUser()!=null)
        {
            if (targetUser != null)
            {
                if (targetUser.getId() == this.getAuthenticatedUser().getUserId() ||
                        this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))
                {
                    retVal = this.radarTemplateRepository.findAssociatedRadarTemplates(targetUser.getId());
                }
            }
        }

        return retVal;
    }

    public boolean associateRadarTemplate(RadarUser targetUser, Long radarTemplateId, boolean shouldAssociate)
    {
        boolean retVal = false;

        RadarTemplate radarTemplate = this.radarTemplateRepository.findOne(radarTemplateId);

        if(radarTemplate!=null && targetUser != null)
        {
            if(shouldAssociate==true)
            {
                // don't allow a user to associate their own radar
                if (radarTemplate.getRadarUser().getId() != targetUser.getId())
                {
                    List<RadarTemplate> associatedRadarTemplates = this.findAssociatedRadarTemplates(targetUser);

                    if(associatedRadarTemplates != null && associatedRadarTemplates.size() < targetUser.getUserType().getGrantValue(UserRights.AllowNAssociatedRadarTemplates))
                    {
                        retVal = this.radarTemplateRepository.saveAssociatedRadarTemplate(targetUser, radarTemplate);
                    }
                }
            }
            else
            {
                retVal = this.radarTemplateRepository.deleteAssociatedRadarTemplate(targetUser, radarTemplate);
            }
        }

        return retVal;
    }

    public boolean associateRadarTemplate(Long radarTemplateId, boolean shouldAssociate)
    {
        RadarUser targetUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
        return this.associateRadarTemplate(targetUser, radarTemplateId, shouldAssociate);
    }
}
