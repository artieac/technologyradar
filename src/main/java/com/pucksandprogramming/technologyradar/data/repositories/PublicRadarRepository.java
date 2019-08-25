package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarItemEntity;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PublicRadarRepository extends RadarRepositoryBase
{
    private static final Logger logger = Logger.getLogger(PublicRadarRepository.class);

    @Override
    public List<Radar> findByUserId(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();

        List<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, true);
        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public Radar findByUserRadarId(Long radarUserId, Long radarId)
    {
        Radar retVal = null;

        RadarEntity foundItem = this.entityRepository.findByIdAndRadarUserIdAndIsPublished(radarId, radarUserId, true);

        if(foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    @Override
    public List<Radar> findByUserAndType(Long radarUserId, Long radarTypeId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  this.entityManager.createNamedQuery("public_FindByUserTypeAndIsPublished");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTypeId", radarTypeId);
        query.setParameter("isPublished", true);
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    @Override
    public List<Radar> findByRadarSubjectId(Long radarSubjectIdf)
    {
        List<Radar> retVal = new ArrayList();
        Query query = entityManager.createNamedQuery("public_FindByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectIdf);
        List<RadarEntity> foundItems = query.getResultList();

        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<Radar> findNotOwnedByRadarSubjectAndUser(Long radarUserId, Long radarSubjectId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("public_FindNotOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    @Override
    public List<Radar> findOwnedByTechnologyId(Long radarUserId, Long radarSubjectId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("public_FindOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);

        List<RadarEntity> foundItems = query.getResultList();
        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<RadarItem> findCurrentByType(Long radarUserId, Long radarTypeId)
    {
        List<RadarItem> retVal = new ArrayList<RadarItem>();

        Query query = entityManager.createNamedQuery("public_PublicFindCurrentRadarItemsByRadarType");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTypeId", radarTypeId);

        List<RadarItemEntity> foundItems = query.getResultList();

        if(foundItems!=null)
        {
            for(RadarItemEntity foundItem : foundItems)
            {
                retVal.add(this.modelMapper.map(foundItem, RadarItem.class));
            }
        }

        return retVal;
    }

    public Radar findMostRecentByUserIdRadarTypeAndPublished(Long userId, Long radarTypeId, boolean publishedOnly)
    {
        Radar retVal = null;
        String maxQuery = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,";
        maxQuery += " ta.RadarTypeId as RadarTypeId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        maxQuery += " FROM TechnologyAssessments ta WHERE ta.id =";
        maxQuery += " (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId";
        maxQuery += "  AND ta2.RadarTypeId = :radarTypeId";

        if(publishedOnly==true)
        {
            maxQuery += " AND ta2.IsPublished = :isPublished";
        }

        maxQuery += ")";

        Query q = this.entityManager.createNativeQuery(maxQuery, RadarEntity.class);
        q.setParameter("radarUserId", userId);
        q.setParameter("radarTypeId", radarTypeId);

        if(publishedOnly==true)
        {
            q.setParameter("isPublished", publishedOnly);
        }

        RadarEntity foundItem = (RadarEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public RadarItem getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(Long radarUserId, Long previousRadarInstanceId, Long radarSubjectId)
    {
        RadarItem retVal = null;

        Query query = entityManager.createNamedQuery("getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("previousRadarInstanceId", previousRadarInstanceId);
        List<RadarItemEntity> foundItems = query.getResultList();

        if (foundItems != null && foundItems.isEmpty()==false)
        {
            retVal = this.modelMapper.map(foundItems.get(0), RadarItem.class);
        }

        return retVal;

    }
}
