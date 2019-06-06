package com.pucksandprogramming.technologyradar.data.repositories;

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

    public List<Radar> findAllByRadarUserAndRadarTypeId(Long radarUserId, Long radarTypeId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndRadarTypeId(radarUserId, radarTypeId);

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public List<Radar> findAllByRadarUserAndIsPublished(Long radarUserId, boolean isPublished)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, isPublished);

        for (RadarInstanceEntity foundItem : foundItems)
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

        Query q = this.entityManager.createNativeQuery(query, RadarInstanceEntity.class);
        q.setParameter(1, technologyId);
        List<RadarInstanceEntity> foundItems = q.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, Radar.class));
        }

        return retVal;
    }

    public Radar findByName(String radarInstanceName)
    {
        Radar retVal = null;

        RadarInstanceEntity foundItem = this.entityRepository.findByName(radarInstanceName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
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

    public Radar findMostRecentByUserIdAndPublishedOnly(Long userId, boolean publishedOnly)
    {
        Radar retVal = null;
        String maxQuery = "select MAX(ta.Id) as Id, ta.Name as Name, ta.AssessmentDate as AssessmentDate, ta.RadarUserId as RadarUserId, ta.IsPublished as IsPublished, ta.IsLocked as IsLocked";
        maxQuery += " FROM TechnologyAssessments ta WHERE ta.RadarUserId = ?1";

        if(publishedOnly==true)
        {
            maxQuery += " AND ta.IsPublished = 1";
        }

        Query q = this.entityManager.createNativeQuery(maxQuery, RadarInstanceEntity.class);
        q.setParameter(1, userId);
        RadarInstanceEntity foundItem = (RadarInstanceEntity)q.getSingleResult();

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
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
            radarInstanceEntity.setRadarType(radarTypeDAO.findOne(itemToSave.getRadarType().getId()));

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