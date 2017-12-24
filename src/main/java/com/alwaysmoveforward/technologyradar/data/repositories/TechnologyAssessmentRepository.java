package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyAssessmentEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyAssessmentItemEntity;
import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;
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
public class TechnologyAssessmentRepository extends SimpleDomainRepository<TechnologyAssessment, TechnologyAssessmentEntity, TechnologyAssessmentDAO, Long>
{
    private static final Logger logger = Logger.getLogger(TechnologyAssessmentRepository.class);

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
    TechnologyAssessmentItemDAO technologyAssessmentItemDAO;

    @Autowired
    public void setEntityRepository(TechnologyAssessmentDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public TechnologyAssessmentRepository()
    {
        super(TechnologyAssessment.class);
    }

    public List<TechnologyAssessment> findAll()
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        Iterable<TechnologyAssessmentEntity> foundItems = this.entityRepository.findAll();

        for (TechnologyAssessmentEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, TechnologyAssessment.class));
        }

        return retVal;
    }


    public List<TechnologyAssessment> findAllByRadarUser(Long radarUserId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        Iterable<TechnologyAssessmentEntity> foundItems = this.entityRepository.findAllByRadarUserId(radarUserId);

        for (TechnologyAssessmentEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, TechnologyAssessment.class));
        }

        return retVal;
    }

    public List<TechnologyAssessment> findAllByTechnologyId(Long technologyId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        String query = "select ta.Id, ta.Name, ta.AssessmentDate, ta.AssessmentTeamId";
        query += " FROM TechnologyAssessments ta WHERE ta.Id IN (SELECT TechnologyAssessmentId FROM TechnologyAssessmentItems WHERE TechnologyId = ?1)";

        Query q = this.entityManager.createNativeQuery(query, TechnologyAssessmentEntity.class);
        q.setParameter(1, technologyId);
        List<TechnologyAssessmentEntity> foundItems = q.getResultList();

        for (TechnologyAssessmentEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, TechnologyAssessment.class));
        }

        return retVal;
    }

    public TechnologyAssessment findByName(String assessmentName)
    {
        TechnologyAssessment retVal = null;

        TechnologyAssessmentEntity foundItem = this.entityRepository.findByName(assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, TechnologyAssessment.class);
        }

        return retVal;
    }

    public TechnologyAssessment findByIdAndName(Long teamId, String assessmentName)
    {
        TechnologyAssessment retVal = null;

        TechnologyAssessmentEntity foundItem = this.entityRepository.findByIdAndName(teamId, assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, TechnologyAssessment.class);
        }

        return retVal;
    }

    @Override
    public TechnologyAssessment save(TechnologyAssessment technologyAssessment)
    {
        TechnologyAssessmentEntity itemToSave = null;

        if(technologyAssessment!=null && technologyAssessment.getId() != null)
        {
            itemToSave = this.entityRepository.findOne(technologyAssessment.getId());
        }
        else
        {
            itemToSave = new TechnologyAssessmentEntity();
        }

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(itemToSave != null)
        {
            itemToSave.setAssessmentDate(technologyAssessment.getAssessmentDate());
            itemToSave.setName(technologyAssessment.getName());
            itemToSave.setRadarUser(radarUserDAO.findOne(technologyAssessment.getRadarUser().getId()));

            for(int i = 0; i < technologyAssessment.getTechnologyAssessmentItems().size(); i++)
            {
                TechnologyAssessmentItem assessmentItem = technologyAssessment.getTechnologyAssessmentItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < itemToSave.getTechnologyAssessmentItems().size(); j++)
                {
                    TechnologyAssessmentItemEntity itemToSaveAssessmentItem = itemToSave.getTechnologyAssessmentItems().get(j);

                    if(itemToSaveAssessmentItem.getTechnology().getId()==assessmentItem.getTechnology().getId())
                    {
                        foundMatch = true;
                        itemToSaveAssessmentItem.setDetails(assessmentItem.getDetails());
                        itemToSaveAssessmentItem.setAssessor(assessmentItem.getAssessor());
                        itemToSaveAssessmentItem.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                        itemToSaveAssessmentItem.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    TechnologyAssessmentItemEntity newItem = new TechnologyAssessmentItemEntity();
                    newItem.setTechnologyAssessment(itemToSave);
                    newItem.setDetails(assessmentItem.getDetails());
                    newItem.setAssessor(assessmentItem.getAssessor());
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
                        targetTechnology.setDescription(assessmentItem.getTechnology().getDescription());
                        targetTechnology.setUrl(assessmentItem.getTechnology().getUrl());
                        targetTechnology.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getTechnology().getRadarCategory().getId()));
                        technologyDAO.save(targetTechnology);
                    }

                    newItem.setTechnology(targetTechnology);
                    technologyAssessmentItemDAO.save(newItem);

                    itemToSave.getTechnologyAssessmentItems().add(newItem);
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