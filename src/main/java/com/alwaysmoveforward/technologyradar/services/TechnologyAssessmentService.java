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
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public TechnologyAssessmentService(TechnologyAssessmentRepository technologyAssessmentRepository,
                                       TechnologyRepository technologyRepository,
                                       RadarRingRepository radarRingRepository,
                                       RadarCategoryRepository radarCategoryRepository,
                                       RadarUserRepository radarUserRepository)
    {
        this.technologyAssessmentRepository = technologyAssessmentRepository;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public TechnologyAssessment createDefault(RadarUser radarUser)
    {
        TechnologyAssessment retVal = new TechnologyAssessment();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setRadarUser(radarUser);
        retVal.setTechnologyAssessmentItems(new ArrayList<TechnologyAssessmentItem>());
        return retVal;
    }

    public TechnologyAssessment findById(Long assessmentId)
    {
        return this.technologyAssessmentRepository.findOne(assessmentId);
    }

    public List<TechnologyAssessment> findByRadarUserId(Long radarUserId)
    {
        List<TechnologyAssessment> retVal = new ArrayList<TechnologyAssessment>();

        RadarUser foundTeam = this.radarUserRepository.findOne(radarUserId);

        if(foundTeam!=null)
        {
            retVal = this.technologyAssessmentRepository.findAllByRadarUser(foundTeam.getId());
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

    public TechnologyAssessment addRadarUserAssessment(Long radarUserId, String name)
    {
        TechnologyAssessment retVal = null;

        if(!name.isEmpty())
        {
            TechnologyAssessment existingTeamAssessment = this.technologyAssessmentRepository.findByIdAndName(radarUserId, name);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(existingTeamAssessment == null && radarUser != null)
            {
                retVal = this.createDefault(radarUser);
                retVal.setName(name);
                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }

    public TechnologyAssessment updateUserAssessment(Long radarUserId, long assessmentId, String name)
    {
        TechnologyAssessment retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.technologyAssessmentRepository.findOne(assessmentId);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUserId)
            {
                retVal.setName(name);
                this.technologyAssessmentRepository.save(retVal);
            }
        }

        return retVal;
    }

    public TechnologyAssessment addRadarItem(RadarUser radarUser, Long assessmentId, String technologyName, String technologyDescription, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
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

            retVal = this.addRadarItem(radarUser, assessmentId, targetTechnology, radarRingId, confidenceLevel, assessmentDetails);
        }

        return retVal;
    }

    public TechnologyAssessment addRadarItem(RadarUser radarUser, Long assessmentId, Technology targetTechnology, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        TechnologyAssessment retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.technologyAssessmentRepository.findOne(assessmentId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
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

    public TechnologyAssessmentItem updateAssessmentItem(Long assessmentId, Long assessmentItemId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        TechnologyAssessmentItem retVal = null;

        if(assessmentId > 0 && assessmentItemId > 0 && radarRingId > 0)
        {
            TechnologyAssessment assessment = this.technologyAssessmentRepository.findOne(assessmentId);

            if(assessment != null)
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                assessment.updateAssessmentItem(assessmentItemId, radarRing, confidenceLevel, assessmentDetails);
                this.technologyAssessmentRepository.save(assessment);
            }
        }
        return retVal;
    }
}
