package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.dto.TechnologyAssessmentItemDTO;
import com.alwaysmoveforward.technologyradar.data.dto.TechnologyDTO;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;
import com.alwaysmoveforward.technologyradar.data.dao.*;
import com.alwaysmoveforward.technologyradar.data.dto.TechnologyAssessmentDTO;
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
public class TechnologyAssessmentRepository extends SimpleDomainRepository<TechnologyAssessment, TechnologyAssessmentDTO, TechnologyAssessmentDAO, Long>
{
    private static final Logger logger = Logger.getLogger(TechnologyAssessmentRepository.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    TechnologyDAO technologyDAO;

    @Autowired
    RadarStateDAO radarStateDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    AssessmentTeamDAO assessmentTeamDAO;

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

        Iterable<TechnologyAssessmentDTO> foundItems = this.entityRepository.findAll();

        for (TechnologyAssessmentDTO foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, TechnologyAssessment.class));
        }

        return retVal;
    }


    public List<TechnologyAssessment> findAllByTeam(Long teamId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        Iterable<TechnologyAssessmentDTO> foundItems = this.entityRepository.findAllByAssessmentTeamId(teamId);

        for (TechnologyAssessmentDTO foundItem : foundItems)
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

        Query q = this.entityManager.createNativeQuery(query, TechnologyAssessmentDTO.class);
        q.setParameter(1, technologyId);
        List<TechnologyAssessmentDTO> foundItems = q.getResultList();

        for (TechnologyAssessmentDTO foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, TechnologyAssessment.class));
        }

        return retVal;
    }

    public TechnologyAssessment findByName(String assessmentName)
    {
        TechnologyAssessment retVal = null;

        TechnologyAssessmentDTO foundItem = this.entityRepository.findByName(assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, TechnologyAssessment.class);
        }

        return retVal;
    }

    @Override
    public TechnologyAssessment save(TechnologyAssessment technologyAssessment)
    {
        TechnologyAssessmentDTO itemToSave = this.entityRepository.findOne(technologyAssessment.getId());

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(itemToSave != null)
        {
            itemToSave.setAssessmentDate(technologyAssessment.getAssessmentDate());
            itemToSave.setName(technologyAssessment.getName());
            itemToSave.setAssessmentTeam(assessmentTeamDAO.findOne(technologyAssessment.getAssessmentTeam().getId()));

            for(int i = 0; i < technologyAssessment.getTechnologyAssessmentItems().size(); i++)
            {
                TechnologyAssessmentItem assessmentItem = technologyAssessment.getTechnologyAssessmentItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < itemToSave.getTechnologyAssessmentItems().size(); j++)
                {
                    TechnologyAssessmentItemDTO itemToSaveAssessmentItem = itemToSave.getTechnologyAssessmentItems().get(j);

                    if(itemToSaveAssessmentItem.getTechnology().getId()==assessmentItem.getTechnology().getId())
                    {
                        foundMatch = true;
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    TechnologyAssessmentItemDTO newItem = new TechnologyAssessmentItemDTO();
                    newItem.setTechnologyAssessment(itemToSave);
                    newItem.setDetails(assessmentItem.getDetails());
                    newItem.setAssessor(assessmentItem.getAssessor());
                    newItem.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getRadarCategory().getId()));
                    newItem.setRadarState(radarStateDAO.findOne(assessmentItem.getRadarState().getId()));

                    TechnologyDTO targetTechnology = technologyDAO.findOne(assessmentItem.getTechnology().getId());

                    if(targetTechnology == null)
                    {
                        targetTechnology = technologyDAO.findByName(assessmentItem.getTechnology().getName());
                    }

                    if(targetTechnology == null)
                    {
                        targetTechnology = new TechnologyDTO();
                        targetTechnology.setName(newItem.getTechnology().getName());
                        targetTechnology.setCreator(newItem.getTechnology().getCreator());
                        targetTechnology.setCreateDate(newItem.getTechnology().getCreateDate());
                        targetTechnology.setDescription(newItem.getTechnology().getDescription());
                        targetTechnology.setUrl(newItem.getTechnology().getUrl());
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