package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarItemEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PublicRadarRepository extends RadarRepositoryBase {
    private static final Logger logger = Logger.getLogger(PublicRadarRepository.class);

    @Autowired
    public PublicRadarRepository(RadarMapper modelMapper,
                                 RadarDAO radarDAO,
                                 EntityManager entityManager,
                                 TechnologyDAO technologyDAO,
                                 RadarRingDAO radarRingDAO,
                                 RadarCategoryDAO radarCategoryDAO,
                                 RadarUserDAO radarUserDAO,
                                 RadarItemDAO radarItemDAO,
                                 RadarTemplateDAO radarTemplateDAO) {
        super(modelMapper, radarDAO, entityManager, technologyDAO, radarRingDAO, radarCategoryDAO, radarUserDAO, radarItemDAO, radarTemplateDAO);
    }

    @Override
    public List<Radar> findByUserId(Long radarUserId) {
        List<Radar> retVal = new ArrayList<>();

        List<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, true);
        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public Optional<Radar> findByUserRadarId(Long radarUserId, Long radarId) {
        RadarEntity foundItem = this.entityRepository.findByIdAndRadarUserIdAndIsPublished(radarId, radarUserId, true);

        if(foundItem != null) {
            return Optional.of(this.modelMapper.map(foundItem, Radar.class));
        }

        return Optional.empty();
    }

    @Override
    public List<Radar> findByUserAndType(Long radarUserId, Long radarTemplateId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  this.entityManager.createNamedQuery("public_FindByUserTypeAndIsPublished");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTemplateId", radarTemplateId);
        query.setParameter("isPublished", true);
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    @Override
    public List<Radar> findByRadarSubjectId(Long radarSubjectIdf) {
        List<Radar> retVal = new ArrayList();
        Query query = entityManager.createNamedQuery("public_FindByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectIdf);
        List<RadarEntity> foundItems = query.getResultList();

        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<Radar> findNotOwnedByRadarSubjectAndUser(Long radarUserId, Long radarSubjectId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("public_FindNotOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems) {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    @Override
    public List<Radar> findOwnedByTechnologyId(Long radarUserId, Long radarSubjectId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("public_FindOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);

        List<RadarEntity> foundItems = query.getResultList();
        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<RadarItem> findCurrentByType(Long radarUserId, Long radarTemplateId) {
        List<RadarItem> retVal = new ArrayList<RadarItem>();

        Query query = entityManager.createNamedQuery("public_PublicFindCurrentRadarItemsByRadarTemplate");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTemplateId", radarTemplateId);

        List<RadarItemEntity> foundItems = query.getResultList();

        if(foundItems!=null) {
            for(RadarItemEntity foundItem : foundItems) {
                retVal.add(this.modelMapper.map(foundItem, RadarItem.class));
            }
        }

        return retVal;
    }

    public Optional<Radar> findMostRecentByUserIdRadarTemplateAndPublished(Long userId, Long radarTemplateId, boolean publishedOnly) {
        String maxQuery = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,";
        maxQuery += " ta.RadarTemplateId as RadarTemplateId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        maxQuery += " FROM TechnologyAssessments ta WHERE ta.id =";
        maxQuery += " (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId";
        maxQuery += "  AND ta2.RadarTemplateId = :radarTemplateId";

        if(publishedOnly==true) {
            maxQuery += " AND ta2.IsPublished = :isPublished";
        }

        maxQuery += ")";

        Query q = this.entityManager.createNativeQuery(maxQuery, RadarEntity.class);
        q.setParameter("radarUserId", userId);
        q.setParameter("radarTemplateId", radarTemplateId);

        if(publishedOnly==true) {
            q.setParameter("isPublished", publishedOnly);
        }

        RadarEntity foundItem = (RadarEntity)q.getSingleResult();

        if (foundItem != null) {
            return Optional.ofNullable(this.modelMapper.map(foundItem, Radar.class));
        }

        return Optional.empty();
    }

    public Optional<RadarItem> getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(Long radarUserId, Long previousRadarInstanceId, Long radarSubjectId)
    {
        Query query = entityManager.createNamedQuery("getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("previousRadarInstanceId", previousRadarInstanceId);
        List<RadarItemEntity> foundItems = query.getResultList();

        if (foundItems != null && foundItems.isEmpty()==false) {
            return Optional.ofNullable(this.modelMapper.map(foundItems.get(0), RadarItem.class));
        }

        return Optional.empty();
    }
}
