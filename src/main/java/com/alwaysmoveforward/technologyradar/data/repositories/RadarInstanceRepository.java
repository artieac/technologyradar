package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceItemEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstance;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarInstanceItem;
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
    public RadarInstance save(RadarInstance radarInstance)
    {
        RadarInstanceEntity itemToSave = null;

        if(radarInstance !=null && radarInstance.getId() != null)
        {
            itemToSave = this.entityRepository.findOne(radarInstance.getId());
        }
        else
        {
            itemToSave = new RadarInstanceEntity();
        }

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(itemToSave != null)
        {
            itemToSave.setAssessmentDate(radarInstance.getAssessmentDate());
            itemToSave.setName(radarInstance.getName());
            itemToSave.setRadarUser(radarUserDAO.findOne(radarInstance.getRadarUser().getId()));

            for(int i = 0; i < radarInstance.getRadarInstanceItems().size(); i++)
            {
                RadarInstanceItem assessmentItem = radarInstance.getRadarInstanceItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < itemToSave.getRadarInstanceItems().size(); j++)
                {
                    RadarInstanceItemEntity itemToSaveAssessmentItem = itemToSave.getRadarInstanceItems().get(j);

                    if(itemToSaveAssessmentItem.getTechnology().getId()==assessmentItem.getTechnology().getId())
                    {
                        foundMatch = true;
                        itemToSaveAssessmentItem.setDetails(assessmentItem.getDetails());
                        itemToSaveAssessmentItem.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                        itemToSaveAssessmentItem.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    RadarInstanceItemEntity newItem = new RadarInstanceItemEntity();
                    newItem.setRadarInstance(itemToSave);
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

                    itemToSave.getRadarInstanceItems().add(newItem);
                }
            }

            if(itemToSave != null && itemToSave.getRadarInstanceItems() != null)
            {
                for(int i = itemToSave.getRadarInstanceItems().size() - 1; i >= 0 ; i--) {
                    RadarInstanceItemEntity itemToSaveAssessmentItem = itemToSave.getRadarInstanceItems().get(i);

                    boolean foundMatch = false;

                    for (int j = 0; j < radarInstance.getRadarInstanceItems().size(); j++) {
                        RadarInstanceItem assessmentItem = radarInstance.getRadarInstanceItems().get(j);

                        if (assessmentItem.getTechnology().getId() == itemToSaveAssessmentItem.getTechnology().getId()) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch == false)
                    {
                        itemToSave.getRadarInstanceItems().remove(itemToSaveAssessmentItem);
                        radarInstanceItemDAO.delete(itemToSaveAssessmentItem);
                    }
                }
            }
        }

        if(itemToSave != null)
        {
            this.entityRepository.save(itemToSave);
        }

        return null;
    }
}