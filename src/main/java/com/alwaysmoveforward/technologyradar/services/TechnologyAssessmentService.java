package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.*;
import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessmentItem;
import com.alwaysmoveforward.technologyradar.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
@Component
public class TechnologyAssessmentService
{
    private TechnologyRepository technologyRepository;
    private TechnologyAssessmentRepository technologyAssessmentRepository;
    private AssessmentTeamRepository assessmentTeamRepository;
    private RadarStateRepository radarStateRepository;
    private RadarCategoryRepository radarCategoryRepository;

    @Autowired
    public TechnologyAssessmentService(TechnologyAssessmentRepository technologyAssessmentRepository,
                                       AssessmentTeamRepository assessmentTeamRepository,
                                       TechnologyRepository technologyRepository,
                                       RadarStateRepository radarStateRepository,
                                       RadarCategoryRepository radarCategoryRepository)
    {
        this.technologyAssessmentRepository = technologyAssessmentRepository;
        this.assessmentTeamRepository = assessmentTeamRepository;
        this.technologyRepository = technologyRepository;
        this.radarStateRepository = radarStateRepository;
        this.radarCategoryRepository = radarCategoryRepository;
    }

    public TechnologyAssessment createDefault(AssessmentTeam assessmentTeam)
    {
        TechnologyAssessment retVal = new TechnologyAssessment();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setAssessmentTeam(assessmentTeam);
        retVal.setTechnologyAssessmentItems(new ArrayList<TechnologyAssessmentItem>());
        return retVal;
    }

    public Technology createDefaultTechnology(){
        Technology retVal = new Technology();
        retVal.setId(new Long(0));
        retVal.setCreator("None");
        retVal.setName("Nothing");
        retVal.setDescription("Nothing");
        retVal.setCreateDate(new Date());
        return retVal;
    }

    public TechnologyAssessment findById(Long assessmentId)
    {
        return this.technologyAssessmentRepository.findOne(assessmentId);
    }

    public List<TechnologyAssessment> getTeamAssessments(Long teamId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        AssessmentTeam foundTeam = this.assessmentTeamRepository.findOne(teamId);

        if(foundTeam!=null)
        {
            retVal = this.technologyAssessmentRepository.findAllByTeam(foundTeam.getId());
        }

        return retVal;
    }

    public Technology findTechnologyById(Long technologyId)
    {
        return this.technologyRepository.findOne(technologyId);
    }

    public List<TechnologyAssessment> getTechnologyAssessmentsByTechnologyId(Long technologyId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.technologyAssessmentRepository.findAllByTechnologyId(foundItem.getId());
        }

        return retVal;
    }

    public List<TechnologyAssessment> findByTeamId(Long teamId)
    {
        return this.technologyAssessmentRepository.findAllByTeam(teamId);
    }

    public TechnologyAssessment addTeamAssessment(Long teamId, String name)
    {
        TechnologyAssessment retVal = null;

        if(!name.isEmpty())
        {
            TechnologyAssessment existingTeamAssessment = this.technologyAssessmentRepository.findByName(name);
            AssessmentTeam assessmentTeam = this.assessmentTeamRepository.findOne(teamId);

            if(existingTeamAssessment == null && assessmentTeam != null)
            {
                retVal = this.createDefault(assessmentTeam);
                retVal.setName(name);
                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }

    public TechnologyAssessment updateTeamAssessment(Long teamId, long assessmentId, String name)
    {
        TechnologyAssessment retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.technologyAssessmentRepository.findOne(assessmentId);
            AssessmentTeam assessmentTeam = this.assessmentTeamRepository.findOne(teamId);

            if(retVal != null && assessmentTeam != null && retVal.getAssessmentTeam().getId() == teamId)
            {
                retVal.setName(name);
                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }

    public TechnologyAssessment addRadarItem(Long teamId, Long assessmentId, String technologyName, Long radarStateId, Long radarCategoryId)
    {
        TechnologyAssessment retVal = null;

        if(!technologyName.isEmpty())
        {
            retVal = this.technologyAssessmentRepository.findOne(assessmentId);
            AssessmentTeam assessmentTeam = this.assessmentTeamRepository.findOne(teamId);

            if(retVal != null && assessmentTeam != null && retVal.getAssessmentTeam().getId() == teamId)
            {
                Technology targetTechnology = this.technologyRepository.findByName(technologyName);

                if(targetTechnology == null)
                {
                    targetTechnology = new Technology();
                    targetTechnology.setName(technologyName);
                    targetTechnology.setCreateDate(new Date());
                    targetTechnology.setDescription("");
                }

                boolean alreadyIncluded = false;

                for(int i = 0; i < retVal.getTechnologyAssessmentItems().size(); i++)
                {
                    if(retVal.getTechnologyAssessmentItems().get(i).getTechnology().getId()==targetTechnology.getId())
                    {
                        alreadyIncluded = true;
                        break;
                    }
                }

                if(alreadyIncluded == false)
                {
                    TechnologyAssessmentItem newItem = new TechnologyAssessmentItem();
                    newItem.setAssessor("Test");
                    newItem.setDetails("Some stuff");
                    newItem.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
                    newItem.setRadarState(this.radarStateRepository.findOne(radarStateId));
                    newItem.setTechnology(targetTechnology);
                    retVal.getTechnologyAssessmentItems().add(newItem);
                }

                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }
}
