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

/**
 * Created by acorrea on 10/21/2016.
 */
@Repository
public class FullRadarRepository extends RadarRepositoryBase {
    private static final Logger logger = Logger.getLogger(FullRadarRepository.class);

    @Autowired
    public FullRadarRepository(RadarMapper modelMapper,
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
        List<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);
        return this.mapList(foundItems);
    }

    @Override
    public Optional<Radar> findByUserRadarId(Long radarUserId, Long radarId) {
        RadarEntity targetItem = this.entityRepository.findByIdAndRadarUserId(radarId, radarUserId);

        if(targetItem!=null){
            return Optional.of(this.modelMapper.map(targetItem, Radar.class));
        }

        return Optional.empty();
    }

    @Override
    public List<Radar> findByUserAndType(Long radarUserId, Long radarTemplateId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  null;
        query = this.entityManager.createNamedQuery("history_FindAllByUserAndType");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTemplateId", radarTemplateId);

        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems) {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    @Override
    public List<Radar> findByRadarSubjectId(Long radarSubjectIdf) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("history_FindByTechnologyId");
        query.setParameter("technologyId", radarSubjectIdf);
        List<RadarEntity> foundItems = query.getResultList();

        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<Radar> findNotOwnedByRadarSubjectAndUser(Long radarUserId, Long radarSubjectId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("history_FindNotOwnedByTechnologyId");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarEntity> foundItems = query.getResultList();

        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<Radar> findOwnedByTechnologyId(Long radarUserId, Long radarSubjectId) {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("history_FindOwnedByTechnologyId");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarEntity> foundItems = query.getResultList();

        retVal = this.mapList(foundItems);

        return retVal;
    }

    @Override
    public List<RadarItem> findCurrentByType(Long radarUserId, Long radarTemplateId) {
        List<RadarItem> retVal = new ArrayList<RadarItem>();

        Query query = entityManager.createNamedQuery("history_PublicFindCurrentRadarItemsByRadarTemplate");
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

    public List<Radar> findAll() {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarEntity> foundItems = this.entityRepository.findAll();

        for (RadarEntity foundItem : foundItems) {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
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
}