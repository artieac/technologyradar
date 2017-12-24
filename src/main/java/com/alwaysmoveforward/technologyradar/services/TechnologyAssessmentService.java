package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.*;
import com.alwaysmoveforward.technologyradar.domainmodel.*;
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
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;

    @Autowired
    public TechnologyAssessmentService(TechnologyAssessmentRepository technologyAssessmentRepository,
                                       AssessmentTeamRepository assessmentTeamRepository,
                                       TechnologyRepository technologyRepository,
                                       RadarRingRepository radarRingRepository,
                                       RadarCategoryRepository radarCategoryRepository)
    {
        this.technologyAssessmentRepository = technologyAssessmentRepository;
        this.assessmentTeamRepository = assessmentTeamRepository;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
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
            TechnologyAssessment existingTeamAssessment = this.technologyAssessmentRepository.findByIdAndName(teamId, name);
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

    public TechnologyAssessment addRadarItem(Long teamId, Long assessmentId, String technologyName, String technologyDescription, String technologyUrl, Long radarRingId, Integer confidenceLevel, Long radarCategoryId, String assessmentDetails, String assessmentEvaluator)
    {
        TechnologyAssessment retVal = null;

        if(!technologyName.isEmpty())
        {
            Technology targetTechnology = this.technologyRepository.findByName(technologyName);

            if (targetTechnology == null)
            {
                targetTechnology = new Technology();
                targetTechnology.setName(technologyName);
                targetTechnology.setCreateDate(new Date());
                targetTechnology.setDescription(technologyDescription);
                targetTechnology.setUrl(technologyUrl);
                targetTechnology.setCreator("");
                targetTechnology.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
            }

            retVal = this.addRadarItem(teamId, assessmentId, targetTechnology, radarRingId, confidenceLevel, assessmentDetails, assessmentEvaluator);
        }

        return retVal;
    }

    public TechnologyAssessment addRadarItem(Long teamId, Long assessmentId, Technology targetTechnology, Long radarRingId, Integer confidenceLevel, String assessmentDetails, String assessmentEvaluator)
    {
        TechnologyAssessment retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.technologyAssessmentRepository.findOne(assessmentId);
            AssessmentTeam assessmentTeam = this.assessmentTeamRepository.findOne(teamId);

            if(retVal != null && assessmentTeam != null && retVal.getAssessmentTeam().getId() == teamId)
            {
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
                    newItem.setAssessor(assessmentEvaluator);
                    newItem.setDetails(assessmentDetails);
                    newItem.setRadarRing(this.radarRingRepository.findOne(radarRingId));
                    newItem.setTechnology(targetTechnology);
                    newItem.setConfidenceFactor(confidenceLevel);
                    retVal.getTechnologyAssessmentItems().add(newItem);
                }

                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }

    public TechnologyAssessmentItem updateAssessmentItem(Long assessmentId, Long assessmentItemId, Long radarRingId, Integer confidenceLevel, String assessmentDetails, String evaluator)
    {
        TechnologyAssessmentItem retVal = null;

        if(assessmentId > 0 && assessmentItemId > 0 && radarRingId > 0)
        {
            TechnologyAssessment assessment = this.technologyAssessmentRepository.findOne(assessmentId);

            if(assessment != null)
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                assessment.updateAssessmentItem(assessmentItemId, radarRing, confidenceLevel, assessmentDetails, evaluator);
                this.technologyAssessmentRepository.save(assessment);
            }
        }
        return retVal;
    }
}
