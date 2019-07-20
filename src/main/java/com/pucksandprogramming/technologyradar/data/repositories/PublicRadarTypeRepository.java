package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PublicRadarTypeRepository extends RadarTypeRepositoryBase
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    public void setEntityRepository(RadarTypeDAO entityRepository) {
        super.setEntityRepository(entityRepository);
    }

    public PublicRadarTypeRepository()
    {

    }

    @Override
    public List<RadarType> findByUser(Long radarUserId)
    {
        Query query =  this.entityManager.createNamedQuery("public_FindMostRecentTypesByPublishedRadarsAndUserId");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public List<RadarType> findByUserAndRadarType(Long radarUserId, String radarTypeId)
    {
        Query query =  this.entityManager.createNamedQuery("public_FindMostRecentTypesByPublishedRadarsUserIdAndRadarTypeId");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTypeId", radarTypeId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    @Override
    public List<RadarType> findSharedRadarTypesExcludeOwned(Long radarUserId)
    {
        Query query =  this.entityManager.createNamedQuery("public_findMostRecentSharedRadarTypesExcludeOwned");
        query.setParameter("radarUserId", radarUserId);
        List<RadarTypeEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }
}
