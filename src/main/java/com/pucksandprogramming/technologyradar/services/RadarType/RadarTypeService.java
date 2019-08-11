package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepositoryBase;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RadarTypeService extends ServiceBase
{
    protected RadarTypeRepositoryFactory radarTypeRepositoryFactory;
    protected FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarTypeService(RadarUserRepository radarUserRepository, RadarTypeRepositoryFactory radarTyperepositoryFactory, FullRadarRepository fullRadarRepository)
    {
        super(radarUserRepository);

        this.radarTypeRepositoryFactory = radarTyperepositoryFactory;
        this.fullRadarRepository = fullRadarRepository;
    }

    public RadarType findOneShared(String radarTypeId, Long version)
    {
        RadarType retVal = null;

        RadarUser currentUser = this.getRadarUserRepository().findOne(this.getAuthenticatedUser().getUserId());
        retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(currentUser).findOne(radarTypeId, version);

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
            retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(currentUser).findOne(radarTypeId, version);
        }

        return retVal;
    }

    public List<RadarType> findByUserId(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(dataOwner).findByUser(userId);

        return retVal;
    }

    public List<RadarType> findMostRecentByUserId(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner != null)
        {
            retVal = this.radarTypeRepositoryFactory.getMostRecentRepository().findByUser(userId);
        }

        return retVal;
    }

    public List<RadarType> findByUserAndRadarType(Long userId, String radarTypeId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);
        retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(dataOwner).findByUserAndRadarType(userId, radarTypeId);

        return retVal;
    }

    public List<RadarType> findSharedRadarTypes(Long userToExclude)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userToExclude);
        RadarTypeRepositoryBase radarRepository = this.radarTypeRepositoryFactory.getRadarTypeRepository(dataOwner);

        if(dataOwner==null)
        {
            retVal = radarRepository.findSharedRadarTypes();
        }
        else
        {
            retVal = radarRepository.findSharedRadarTypesExcludeOwned(userToExclude);
        }

        return retVal;
    }

    public List<RadarType> findByPublishedRadars(Long excludeUserId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser excludeUser = this.getRadarUserRepository().findOne(excludeUserId);

        if(excludeUser==null)
        {
            retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(null).findByPublishedRadars();
        }
        else
        {
            retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(null).findByPublishedRadarsExcludeUser(excludeUserId);
        }

        return retVal;
    }

    public List<RadarType> findOwnedWithRadars(Long dataOwnerId)
    {
        List<RadarType> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(dataOwnerId);

        if(dataOwner==null)
        {
            retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(null).findByPublishedRadars();
        }
        else
        {
            retVal = this.radarTypeRepositoryFactory.getRadarTypeRepository(null).findOwnedWithRadars(dataOwner.getId());
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
            RadarTypeRepositoryBase radarRepository = this.radarTypeRepositoryFactory.getRadarTypeRepository(dataOwner);

            retVal = radarRepository.findOne(radarTypeUpdates.getId(), radarTypeUpdates.getVersion());

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
                retVal = radarRepository.save(radarTypeUpdates);
            }
        }

        return retVal;
    }

}
