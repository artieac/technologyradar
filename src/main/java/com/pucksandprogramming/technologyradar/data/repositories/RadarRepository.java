package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
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
public class RadarRepository extends SimpleDomainRepository<Radar, RadarEntity, RadarDAO, Long>
{
    private static final Logger logger = Logger.getLogger(RadarRepository.class);

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
    public void setEntityRepository(RadarDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarRepository()
    {
        super(Radar.class);
    }

    @Override
    protected RadarEntity findOne(Radar domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public List<Radar> findAll()
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarEntity> foundItems = this.entityRepository.findAll();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId){
        Radar retVal = null;

        RadarEntity targetItem = this.entityRepository.findByIdAndRadarUserId(radarInstanceId, radarUserId);

        if(targetItem!=null){
            retVal = this.modelMapper.map(targetItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUser(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUserAndIsPublished(Long radarUserId, boolean publishedOnly)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        List<RadarEntity> foundItems = null;

        if(publishedOnly==true)
        {
            foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, true);
        }
        else
        {
            foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);
        }

        for (RadarEntity foundItem : foundItems)
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

        RadarEntity foundItem = (RadarEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public Radar findByIdAndRadarUserIdAndPublishedOnly(Long userId, Long radarId, boolean publishedOnly)
    {
        RadarEntity retVal = null;

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

        RadarEntity foundItem = (RadarEntity)query.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByUserTypeAndIsPublished(Long radarUserId, String radarTypeId,  boolean isPublished)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Query query =  this.entityManager.createNamedQuery("findAllByUserTypeAndIsPublished");
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

    public Radar findAMostRecentByUserTypeAndIsPublished(Long userId, String radarTypeId, boolean publishedOnly)
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

        RadarEntity foundItem = (RadarEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByUserTypeVersionAndIsPublished(Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean publishedOnly)
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

        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findAMostRecentByUserTypeVersionAndIsPublished(Long radarUserId, String radarTypeId, Long radarTypeVersion, boolean publishedOnly)
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

        RadarEntity foundItem = (RadarEntity)query.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUserAndRadarTypeId(Long radarUserId, String radarTypeId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarEntity> foundItems = null; //this.entityRepository.findAllByRadarUserIdAndRadarTypeId(radarUserId, radarTypeId);

        for (RadarEntity foundItem : foundItems)
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
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
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
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
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
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
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
        List<RadarEntity> foundItems = query.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByIdAndName(Long radarInstanceId, String assessmentName)
    {
        Radar retVal = null;

        RadarEntity foundItem = this.entityRepository.findByIdAndName(radarInstanceId, assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }


    public Radar findMostRecentByUserIdRadarTypeAndPublished(Long userId, String radarTypeId, boolean publishedOnly)
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
    @Override
    public Radar save(Radar itemToSave)
    {
        RadarEntity radarEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            radarEntity = this.entityRepository.findOne(itemToSave.getId());
        }
        else
        {
            radarEntity = new RadarEntity();
        }

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(radarEntity != null)
        {
            radarEntity.setAssessmentDate(itemToSave.getAssessmentDate());
            radarEntity.setName(itemToSave.getName());
            radarEntity.setRadarUser(radarUserDAO.findOne(itemToSave.getRadarUser().getId()));
            radarEntity.setIsPublished(itemToSave.getIsPublished());
            radarEntity.setIsLocked(itemToSave.getIsLocked());
            VersionedIdEntity versionedIdEntity = new VersionedIdEntity(itemToSave.getRadarType().getId(), itemToSave.getRadarType().getVersion());
            radarEntity.setRadarType(radarTypeDAO.findOne(versionedIdEntity));

            // First remove any deletions
            if(radarEntity != null && radarEntity.getRadarItems() != null)
            {
                for(int i = radarEntity.getRadarItems().size() - 1; i >= 0 ; i--)
                {
                    RadarItemEntity radarInstanceItemEntity = radarEntity.getRadarItems().get(i);

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
                        radarEntity.getRadarItems().remove(radarInstanceItemEntity);
                        radarItemDAO.delete(radarInstanceItemEntity);
                    }
                }
            }

            // then add in any new ones
            for(int i = 0; i < itemToSave.getRadarItems().size(); i++)
            {
                RadarItem assessmentItem = itemToSave.getRadarItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < radarEntity.getRadarItems().size(); j++)
                {
                    RadarItemEntity radarInstanceItemEntity = radarEntity.getRadarItems().get(j);

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
                    newItem.setRadarInstance(radarEntity);
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

                    radarEntity.getRadarItems().add(newItem);
                }
            }
        }

        if(radarEntity != null)
        {
            this.entityRepository.save(radarEntity);
        }

        return this.modelMapper.map(radarEntity, Radar.class);
    }
}