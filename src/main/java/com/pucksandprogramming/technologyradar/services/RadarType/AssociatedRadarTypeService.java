package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssociatedRadarTypeService extends ServiceBase
{
    RadarTypeRepository radarTypeRepository;

    @Autowired
    public AssociatedRadarTypeService(RadarTypeRepository radarTypeRepository, RadarUserRepository radarUserRepository)
    {
        super(radarUserRepository);

        this.radarTypeRepository = radarTypeRepository;
    }

    public List<RadarType> findAssociatedRadarTypes(RadarUser targetUser)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(this.getAuthenticatedUser()!=null)
        {
            if (targetUser != null)
            {
                if (targetUser.getId() == this.getAuthenticatedUser().getUserId() ||
                        this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))
                {
                    retVal = this.radarTypeRepository.findAssociatedRadarTypes(targetUser.getId());
                }
            }
        }

        return retVal;
    }

    public boolean associateRadarType(RadarUser targetUser, Long radarTypeId, boolean shouldAssociate)
    {
        boolean retVal = false;

        RadarType radarType = this.radarTypeRepository.findOne(radarTypeId);

        if(radarType!=null && targetUser != null)
        {
            if(shouldAssociate==true)
            {
                // don't allow a user to associate their own radar
                if (radarType.getRadarUser().getId() != targetUser.getId())
                {
                    List<RadarType> associatedRadarTypes = this.findAssociatedRadarTypes(targetUser);

                    if(associatedRadarTypes != null && associatedRadarTypes.size() < targetUser.getUserType().getGrantValue(UserRights.AllowNAssociatedRadarTypes))
                    {
                        retVal = this.radarTypeRepository.saveAssociatedRadarType(targetUser, radarType);
                    }
                }
            }
            else
            {
                retVal = this.radarTypeRepository.deleteAssociatedRadarType(targetUser, radarType);
            }
        }

        return retVal;
    }

    public boolean associateRadarType(Long radarTypeId, boolean shouldAssociate)
    {
        RadarUser targetUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
        return this.associateRadarType(targetUser, radarTypeId, shouldAssociate);
    }
}
