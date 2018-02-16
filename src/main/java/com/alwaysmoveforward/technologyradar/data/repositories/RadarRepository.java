package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarItemEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.Radar;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarItem;
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

    public List<Radar> findAllByRadarUserAndIsPublished(Long radarUserId, boolean isPublished)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, isPublished);

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllByTechnologyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        String query = "select ta.Id as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        query += " FROM TechnologyAssessments ta WHERE ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = ?1)";

        Query q = this.entityManager.createNativeQuery(query, RadarEntity.class);
        q.setParameter(1, technologyId);
        List<RadarEntity> foundItems = q.getResultList();

        for (RadarEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByName(String radarInstanceName)
    {
        Radar retVal = null;

        RadarEntity foundItem = this.entityRepository.findByName(radarInstanceName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
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

    public Radar findMostRecentByUserIdAndPublishedOnly(Long userId, boolean publishedOnly)
    {
        Radar retVal = null;
        String maxQuery = "select MAX(ta.Id) as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        maxQuery += " FROM TechnologyAssessments ta WHERE ta.RadarUserId = ?1";

        if(publishedOnly==true)
        {
            maxQuery += " AND ta.IsPublished = 1";
        }

        Query q = this.entityManager.createNativeQuery(maxQuery, RadarEntity.class);
        q.setParameter(1, userId);
        RadarEntity foundItem = (RadarEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
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
                        radarInstanceItemEntity.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                        radarInstanceItemEntity.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    RadarItemEntity newItem = new RadarItemEntity();
                    newItem.setRadarInstance(radarEntity);
                    newItem.setDetails(assessmentItem.getDetails());
                    newItem.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                    newItem.setConfidenceFactor(assessmentItem.getConfidenceFactor());

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
                        targetTechnology.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getTechnology().getRadarCategory().getId()));
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