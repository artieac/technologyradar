package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.data.Entities.RadarInstanceEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarItemEntity;
import com.pucksandprogramming.technologyradar.data.Entities.TechnologyEntity;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
@Repository
public class RadarInstanceRepository extends SimpleDomainRepository<Radar, RadarInstanceEntity, RadarInstanceDAO, Long>
{
    private static final Logger logger = Logger.getLogger(RadarInstanceRepository.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    TechnologyDAO technologyDAO;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    RadarItemDAO radarItemDAO;

    @Autowired
    RadarTypeDAO radarTypeDAO;

    @Autowired
    public void setEntityRepository(RadarInstanceDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarInstanceRepository()
    {
        super(Radar.class);
    }

    @Override
    protected RadarInstanceEntity findOne(Radar domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<Radar> findAll()
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAll();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId){
        Radar retVal = null;

        RadarInstanceEntity targetItem = this.entityRepository.findByIdAndRadarUserId(radarInstanceId, radarUserId);

        if(targetItem!=null){
            retVal = this.modelMapper.map(targetItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUser(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUserAndIsPublished(Long radarUserId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        List<RadarInstanceEntity> foundItems = null;

        if(publishedOnly==true)
        {
            foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, true);
        }
        else
        {
            foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);
        }

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findMostRecentByUserIdAndPublishedOnly(Long userId, boolean publishedOnly)
    {
        Radar retVal = null;

        Query q = null;

        if(publishedOnly==true)
        {
            q = this.entityManager.createNamedQuery("findMostRecentByUserAndIsPublished");
            q.setParameter("radarUserId", userId);
            q.setParameter("isPublished", true);
        }
        else
        {
            q = this.entityManager.createNamedQuery("findMostRecentByUser");
            q.setParameter("radarUserId", userId);
        }

        RadarInstanceEntity foundItem = (RadarInstanceEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public Radar findByIdAndRadarUserIdAndPublishedOnly(Long userId, Long radarId, boolean publishedOnly)
    {
        RadarInstanceEntity retVal = null;

        if(publishedOnly==true)
        {
            retVal = this.entityRepository.findByIdAndRadarUserIdAndIsPublished(radarId, userId, true);
        }
        else
        {
            retVal = this.entityRepository.findByIdAndRadarUserId(radarId, userId);
        }

        return this.modelMapper.map(retVal, Radar.class);
    }

    public Radar findMostRecentByUserRadarIdAndPublishedOnly(Long userId, Long radarId, boolean publishedOnly)
    {
        Radar retVal = null;

        Query query = null;

        if(publishedOnly==true)
        {
            query = this.entityManager.createNamedQuery("findMostRecentByUserRadarIdAndIsPublished");
            query.setParameter("radarUserId", userId);
            query.setParameter("radarId", radarId);
            query.setParameter("isPublished", true);
        }
        else
        {
            query = this.entityManager.createNamedQuery("findMostRecentByUserAndRadarId");
            query.setParameter("radarUserId", userId);
            query.setParameter("radarId", radarId);
        }

        RadarInstanceEntity foundItem = (RadarInstanceEntity)query.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByUserTypeAndIsPublished(Long radarUserId, Long radarTypeId,  boolean isPublished)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  this.entityManager.createNamedQuery("findAllByUserTypeAndIsPublished");
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("radarTypeId", radarTypeId);
        query.setParameter("isPublished", true);
        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findAMostRecentByUserTypeAndIsPublished(Long userId, Long radarTypeId, boolean publishedOnly)
    {
        Radar retVal = null;

        Query q = null;

        if(publishedOnly==true)
        {
            q = this.entityManager.createNamedQuery("findMostRecentByUserTypeAndIsPublished");
            q.setParameter("radarUserId", userId);
            q.setParameter("radarTypeId", radarTypeId);
            q.setParameter("isPublished", true);
        }
        else
        {
            q = this.entityManager.createNamedQuery("findMostRecentByUserType");
            q.setParameter("radarUserId", userId);
            q.setParameter("radarTypeId", radarTypeId);
        }

        RadarInstanceEntity foundItem = (RadarInstanceEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByUserTypeVersionAndIsPublished(Long radarUserId, Long radarTypeId, Long radarTypeVersion, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  null;

        if(publishedOnly==true)
        {
            query = this.entityManager.createNamedQuery("findAllByUserTypeVersionAndIsPublished");
            query.setParameter("radarUserId", radarUserId);
            query.setParameter("radarTypeId", radarTypeId);
            query.setParameter("radarTypeVersion", radarTypeVersion);
            query.setParameter("isPublished", true);
        }
        else
        {
            query = this.entityManager.createNamedQuery("findAllByUserTypeVersion");
            query.setParameter("radarUserId", radarUserId);
            query.setParameter("radarTypeId", radarTypeId);
            query.setParameter("radarTypeVersion", radarTypeVersion);
        }

        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findAMostRecentByUserTypeVersionAndIsPublished(Long radarUserId, Long radarTypeId, Long radarTypeVersion, boolean publishedOnly)
    {
        Radar retVal = null;

        Query query = null;

        if(publishedOnly==true)
        {
            query = this.entityManager.createNamedQuery("findMostRecentByUserTypeVersionAndIsPublished");
            query.setParameter("radarUserId", radarUserId);
            query.setParameter("radarTypeId", radarTypeId);
            query.setParameter("radarTypeVersion", radarTypeVersion);
            query.setParameter("isPublished", true);
        }
        else
        {
            query = this.entityManager.createNamedQuery("findMostRecentByUserTypeAndVersion");
            query.setParameter("radarUserId", radarUserId);
            query.setParameter("radarTypeId", radarTypeId);
            query.setParameter("radarTypeVersion", radarTypeVersion);
        }

        RadarInstanceEntity foundItem = (RadarInstanceEntity)query.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUserAndRadarTypeId(Long radarUserId, Long radarTypeId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarInstanceEntity> foundItems = null; //this.entityRepository.findAllByRadarUserIdAndRadarTypeId(radarUserId, radarTypeId);

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllByTechnolgyIdAndRadarUserId(Long technologyId, Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("findAllOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", technologyId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllNotOwnedByTechnolgyIdAndRadarUserId(Long technologyId, Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query = entityManager.createNamedQuery("findAllNotOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", technologyId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findMostNotOwnedByTechnolgyIdAndRadarUserId(Long technologyId, Long radarUserId)
    {
        List<Radar> retVal = new ArrayList();

        Query query = entityManager.createNamedQuery("findMostRecentNotOwnedByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", technologyId);
        query.setParameter("radarUserId", radarUserId);
        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findMostRecentByTechnolgyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList();
        Query query = entityManager.createNamedQuery("findMostRecentByTechnologyIdAndIsPublished");
        query.setParameter("technologyId", technologyId);
        List<RadarInstanceEntity> foundItems = query.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByIdAndName(Long radarInstanceId, String assessmentName)
    {
        Radar retVal = null;

        RadarInstanceEntity foundItem = this.entityRepository.findByIdAndName(radarInstanceId, assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }


    public Radar findMostRecentByUserIdRadarTypeAndPublished(Long userId, Long radarTypeId, boolean publishedOnly)
    {
        Radar retVal = null;
        String maxQuery = "select ta.Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId,";
        maxQuery += " ta.RadarTypeId as RadarTypeId, ta.RadarTypeVersion as RadarTypeVersion, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        maxQuery += " FROM TechnologyAssessments ta WHERE ta.id =";
        maxQuery += " (SELECT MAX(ta2.Id) FROM TechnologyAssessments ta2 WHERE ta2.RadarUserId = :radarUserId";
        maxQuery += "  AND ta2.RadarTypeId = :radarTypeId";

        if(publishedOnly==true)
        {
            maxQuery += " AND ta2.IsPublished = :isPublished";
        }

        maxQuery += ")";

        Query q = this.entityManager.createNativeQuery(maxQuery, RadarInstanceEntity.class);
        q.setParameter("radarUserId", userId);
        q.setParameter("radarTypeId", radarTypeId);

        if(publishedOnly==true)
        {
            q.setParameter("isPublished", publishedOnly);
        }

        RadarInstanceEntity foundItem = (RadarInstanceEntity)q.getSingleResult();

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
    @Override
    public Radar save(Radar itemToSave)
    {
        RadarInstanceEntity radarInstanceEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            radarInstanceEntity = this.entityRepository.findOne(itemToSave.getId());
        }
        else
        {
            radarInstanceEntity = new RadarInstanceEntity();
        }

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(radarInstanceEntity != null)
        {
            radarInstanceEntity.setAssessmentDate(itemToSave.getAssessmentDate());
            radarInstanceEntity.setName(itemToSave.getName());
            radarInstanceEntity.setRadarUser(radarUserDAO.findOne(itemToSave.getRadarUser().getId()));
            radarInstanceEntity.setIsPublished(itemToSave.getIsPublished());
            radarInstanceEntity.setIsLocked(itemToSave.getIsLocked());
            VersionedIdEntity versionedIdEntity = new VersionedIdEntity(itemToSave.getRadarType().getId(), itemToSave.getRadarType().getVersion());
            radarInstanceEntity.setRadarType(radarTypeDAO.findOne(versionedIdEntity));

            // First remove any deletions
            if(radarInstanceEntity != null && radarInstanceEntity.getRadarItems() != null)
            {
                for(int i = radarInstanceEntity.getRadarItems().size() - 1; i >= 0 ; i--)
                {
                    RadarItemEntity radarInstanceItemEntity = radarInstanceEntity.getRadarItems().get(i);

                    boolean foundMatch = false;

                    for (int j = 0; j < itemToSave.getRadarItems().size(); j++) {
                        RadarItem assessmentItem = itemToSave.getRadarItems().get(j);

                        if (assessmentItem.getTechnology().getId() == radarInstanceItemEntity.getTechnology().getId()) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch == false)
                    {
                        radarInstanceEntity.getRadarItems().remove(radarInstanceItemEntity);
                        radarItemDAO.delete(radarInstanceItemEntity);
                    }
                }
            }

            // then add in any new ones
            for(int i = 0; i < itemToSave.getRadarItems().size(); i++)
            {
                RadarItem assessmentItem = itemToSave.getRadarItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < radarInstanceEntity.getRadarItems().size(); j++)
                {
                    RadarItemEntity radarInstanceItemEntity = radarInstanceEntity.getRadarItems().get(j);

                    if(radarInstanceItemEntity.getTechnology().getId()==assessmentItem.getTechnology().getId())
                    {
                        foundMatch = true;
                        radarInstanceItemEntity.setDetails(assessmentItem.getDetails());
                        radarInstanceItemEntity.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getRadarCategory().getId()));
                        radarInstanceItemEntity.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                        radarInstanceItemEntity.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                        radarInstanceItemEntity.setState(assessmentItem.getState());
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    RadarItemEntity newItem = new RadarItemEntity();
                    newItem.setRadarInstance(radarInstanceEntity);
                    newItem.setDetails(assessmentItem.getDetails());
                    newItem.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getRadarCategory().getId()));
                    newItem.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                    newItem.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                    newItem.setState(assessmentItem.getState());

                    TechnologyEntity targetTechnology = null;

                    if(assessmentItem.getTechnology().getId() != null)
                    {
                        targetTechnology = technologyDAO.findOne(assessmentItem.getTechnology().getId());
                    }

                    if(targetTechnology == null)
                    {
                        targetTechnology = technologyDAO.findByName(assessmentItem.getTechnology().getName());
                    }

                    if(targetTechnology == null)
                    {
                        targetTechnology = new TechnologyEntity();
                        targetTechnology.setName(assessmentItem.getTechnology().getName());
                        targetTechnology.setCreator(assessmentItem.getTechnology().getCreator());
                        targetTechnology.setCreateDate(assessmentItem.getTechnology().getCreateDate());
                        targetTechnology.setUrl(assessmentItem.getTechnology().getUrl());
                        technologyDAO.save(targetTechnology);
                    }

                    newItem.setTechnology(targetTechnology);
                    radarItemDAO.save(newItem);

                    radarInstanceEntity.getRadarItems().add(newItem);
                }
            }
        }

        if(radarInstanceEntity != null)
        {
            this.entityRepository.save(radarInstanceEntity);
        }

        return this.modelMapper.map(radarInstanceEntity, Radar.class);
    }
}