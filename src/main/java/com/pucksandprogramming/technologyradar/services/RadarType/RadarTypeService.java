package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RadarTypeService extends ServiceBase
{
    protected RadarTypeRepository radarTypeRepository;
    protected FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarTypeService(RadarUserRepository radarUserRepository, RadarTypeRepository radarTypeRepository, FullRadarRepository fullRadarRepository)
    {
        super(radarUserRepository);

        this.radarTypeRepository = radarTypeRepository;
        this.fullRadarRepository = fullRadarRepository;
    }

    public RadarType findOneShared(String radarTypeId, Long version)
    {
        RadarType retVal = null;

        RadarUser currentUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
        retVal = this.radarTypeRepository.findOne(radarTypeId, version);

        if(retVal.getIsPublished()==false)
        {
            return retVal;
        }

        return retVal;
    }

    public RadarType findOne(String radarTypeId, Long version)
    {
        RadarType retVal = null;

        if(this.getAuthenticatedUser()!=null)
        {
            RadarUser currentUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
            retVal = this.radarTypeRepository.findOne(radarTypeId, version);
        }

        return retVal;
    }

    public List<RadarType> findByUserId(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTypeRepository.findByUser(userId);

        return retVal;
    }

    public List<RadarType> findMostRecentByUserId(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner != null)
        {
            retVal = this.radarTypeRepository.findByUser(userId);
        }

        return retVal;
    }

    public List<RadarType> findByUserAndRadarType(Long userId, String radarTypeId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTypeRepository.findByUserAndRadarType(userId, radarTypeId);

        return retVal;
    }

    public List<RadarType> findSharedRadarTypes(Long userToExclude)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userToExclude);

        if(dataOwner==null)
        {
            retVal = this.radarTypeRepository.findSharedRadarTypes();
        }
        else
        {
            retVal = this.radarTypeRepository.findSharedRadarTypesExcludeOwned(userToExclude);
        }

        return retVal;
    }

    public List<RadarType> findByPublishedRadars(Long excludeUserId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser excludeUser = this.getRadarUserRepository().findOne(excludeUserId);

        if(excludeUser==null)
        {
            retVal = this.radarTypeRepository.findByPublishedRadars();
        }
        else
        {
            retVal = this.radarTypeRepository.findByPublishedRadarsExcludeUser(excludeUserId);
        }

        return retVal;
    }

    public List<RadarType> findOwnedWithRadars(Long dataOwnerId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(dataOwnerId);

        if(dataOwner==null)
        {
            retVal = this.radarTypeRepository.findByPublishedRadars();
        }
        else
        {
            retVal = this.radarTypeRepository.findOwnedWithRadars(dataOwner.getId());
        }

        return retVal;
    }
    public RadarType update(RadarType radarTypeUpdates, Long ownerId)
    {
        RadarType retVal = null;

        // first maek sure it can even save at ll
        if(radarTypeUpdates != null && radarTypeUpdates.getRadarRings() != null && radarTypeUpdates.getRadarRings().size() > 0)
        {
            boolean canSave = false;

            RadarUser dataOwner = this.getRadarUserRepository().findOne(ownerId);

            retVal = this.radarTypeRepository.findOne(radarTypeUpdates.getId(), radarTypeUpdates.getVersion());

            if(retVal==null)
            {
                canSave = true;
            }
            else
            {
                if (dataOwner != null &&
                        dataOwner.getId() == this.getAuthenticatedUser().getUserId())
                {
                    canSave = true;
                }
            }

            if(canSave==true)
            {
                radarTypeUpdates.setRadarUser(dataOwner);
                retVal = this.radarTypeRepository.save(radarTypeUpdates);
            }
        }

        return retVal;
    }

    public boolean deleteRadarType(Long userId, String radarTypeId, Long radarTypeVersion)
    {
        boolean retVal = false;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null)
        {
            RadarType foundItem = this.radarTypeRepository.findOne(radarTypeId, radarTypeVersion);

            if(foundItem.getRadarUser().getId() == userId &&
                (this.getAuthenticatedUser().getUserId()==foundItem.getRadarUser().getId() ||
                this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())))
            {
                List<Radar> userRadars = this.fullRadarRepository.findByUserTypeAndVersion(userId, radarTypeId, radarTypeVersion);

                if(userRadars.size()==0)
                {
                    this.radarTypeRepository.delete(foundItem);
                    retVal = true;
                }
                else
                {
                    this.radarTypeRepository.delete((Iterable)userRadars);

                    foundItem.setState(RadarType.State_InActive);
                    this.radarTypeRepository.save(foundItem);
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
