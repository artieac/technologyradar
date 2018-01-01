package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceItemEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstance;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstanceItem;
import org.apache.catalina.mapper.Mapper;
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
public class RadarInstanceRepository extends SimpleDomainRepository<RadarInstance, RadarInstanceEntity, RadarInstanceDAO, Long>
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
    RadarInstanceItemDAO radarInstanceItemDAO;

    @Autowired
    public void setEntityRepository(RadarInstanceDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarInstanceRepository()
    {
        super(RadarInstance.class);
    }

    public List<RadarInstance> findAll()
    {
        List<RadarInstance> retVal = new ArrayList<RadarInstance>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAll();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarInstance.class));
        }

        return retVal;
    }

    public RadarInstance findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId){
        RadarInstance retVal = null;

        RadarInstanceEntity targetItem = this.entityRepository.findByIdAndRadarUserId(radarInstanceId, radarUserId);

        if(targetItem!=null){
            retVal = this.modelMapper.map(targetItem, RadarInstance.class);
        }

        return retVal;
    }

    public List<RadarInstance> findAllByRadarUser(Long radarUserId)
    {
        List<RadarInstance> retVal = new ArrayList<RadarInstance>();

        Iterable<RadarInstanceEntity> foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarInstance.class));
        }

        return retVal;
    }

    public List<RadarInstance> findAllByTechnologyId(Long technologyId)
    {
        List<RadarInstance> retVal = new ArrayList<RadarInstance>();

        String query = "select ta.Id, ta.Name, ta.AssessmentDate, ta.RadarUserId";
        query += " FROM TechnologyAssessments ta WHERE ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = ?1)";

        Query q = this.entityManager.createNativeQuery(query, RadarInstanceEntity.class);
        q.setParameter(1, technologyId);
        List<RadarInstanceEntity> foundItems = q.getResultList();

        for (RadarInstanceEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, RadarInstance.class));
        }

        return retVal;
    }

    public RadarInstance findByName(String radarInstanceName)
    {
        RadarInstance retVal = null;

        RadarInstanceEntity foundItem = this.entityRepository.findByName(radarInstanceName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, RadarInstance.class);
        }

        return retVal;
    }

    public RadarInstance findByIdAndName(Long radarInstanceId, String assessmentName)
    {
        RadarInstance retVal = null;

        RadarInstanceEntity foundItem = this.entityRepository.findByIdAndName(radarInstanceId, assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, RadarInstance.class);
        }

        return retVal;
    }

    @Override
    public RadarInstance save(RadarInstance itemToSave)
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

            // First remove any deletions
            if(radarInstanceEntity != null && radarInstanceEntity.getRadarInstanceItems() != null)
            {
                for(int i = radarInstanceEntity.getRadarInstanceItems().size() - 1; i >= 0 ; i--)
                {
                    RadarInstanceItemEntity radarInstanceItemEntity = radarInstanceEntity.getRadarInstanceItems().get(i);

                    boolean foundMatch = false;

                    for (int j = 0; j < itemToSave.getRadarInstanceItems().size(); j++) {
                        RadarInstanceItem assessmentItem = itemToSave.getRadarInstanceItems().get(j);

                        if (assessmentItem.getTechnology().getId() == radarInstanceItemEntity.getTechnology().getId()) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch == false)
                    {
                        radarInstanceEntity.getRadarInstanceItems().remove(radarInstanceItemEntity);
                        radarInstanceItemDAO.delete(radarInstanceItemEntity);
                    }
                }
            }

            // then add in any new ones
            for(int i = 0; i < itemToSave.getRadarInstanceItems().size(); i++)
            {
                RadarInstanceItem assessmentItem = itemToSave.getRadarInstanceItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < radarInstanceEntity.getRadarInstanceItems().size(); j++)
                {
                    RadarInstanceItemEntity radarInstanceItemEntity = radarInstanceEntity.getRadarInstanceItems().get(j);

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
                    RadarInstanceItemEntity newItem = new RadarInstanceItemEntity();
                    newItem.setRadarInstance(radarInstanceEntity);
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
                    radarInstanceItemDAO.save(newItem);

                    radarInstanceEntity.getRadarInstanceItems().add(newItem);
                }
            }
        }

        if(radarInstanceEntity != null)
        {
            this.entityRepository.save(radarInstanceEntity);
        }

        return this.modelMapper.map(radarInstanceEntity, RadarInstance.class);
    }
}