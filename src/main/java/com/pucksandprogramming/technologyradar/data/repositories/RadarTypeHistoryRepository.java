package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.OrderColumn;
import javax.persistence.Query;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RadarTypeHistoryRepository extends RadarTypeRepositoryBase
{
    public RadarTypeHistoryRepository()
    {

    }

    @Override
    public List<RadarType> findByUser(Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        List<RadarTypeEntity> foundItems = this.entityRepository.findAllByRadarUserIdOrderByVersionedId(userId);

        if(foundItems != null)
        {
            for (RadarTypeEntity foundItem : foundItems)
            {
                retVal.add(this.modelMapper.map(foundItem, RadarType.class));
            }
        }

        return retVal;
    }

    @Override
    public List<RadarType> findByUserAndRadarType(Long userId, String radarTypeId)
    {
        Query query = entityManager.createNamedQuery("owned_FindHistoryByRadarUserIdAndId");
        query.setParameter("radarUserId", userId);
        query.setParameter("radarTypeId", radarTypeId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public List<RadarType> findSharedRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query = entityManager.createNamedQuery("owned_FindHistorySharedRadarTypesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }


//    public List<RadarType> findAllTypesByUserAandPublishedRadars(Long userId)
//    {
//        Query query = entityManager.createNamedQuery("findAllTypesByPublishedRadars");
//        query.setParameter("radarUserId", userId);
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }







//    public List<RadarType> findOthersRadarTypes(Long radarUserId)
//    {
//        Query query = entityManager.createNamedQuery("findOthersRadarTypes");
//        query.setParameter("radarUserId", radarUserId);
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }

//    public List<RadarType> findAllForPublishedRadars()
//    {
//        Query query = entityManager.createNamedQuery("findAllForPublishedRadars");
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }

//    public List<RadarType> findAllForPublishedRadars(Long radarUserId)
//    {
//        Query query = entityManager.createNamedQuery("findAllForPublishedRadarsExcludeUser");
//        query.setParameter("radarUserId", radarUserId);
//        List<RadarTypeEntity> foundItems = query.getResultList();
//        return this.mapList(foundItems);
//    }


}
