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
public class RadarInstanceService
{
    private TechnologyRepository technologyRepository;
    private RadarInstanceRepository radarInstanceRepository;
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public RadarInstanceService(RadarInstanceRepository radarInstanceRepository,
                                TechnologyRepository technologyRepository,
                                RadarRingRepository radarRingRepository,
                                RadarCategoryRepository radarCategoryRepository,
                                RadarUserRepository radarUserRepository)
    {
        this.radarInstanceRepository = radarInstanceRepository;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public RadarInstance createDefault(RadarUser radarUser)
    {
        RadarInstance retVal = new RadarInstance();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setRadarUser(radarUser);
        retVal.setRadarInstanceItems(new ArrayList<RadarInstanceItem>());
        return retVal;
    }

    public RadarInstance findById(Long radar)
    {
        return this.radarInstanceRepository.findOne(radar);
    }

    public List<RadarInstance> findByRadarUserId(Long radarUserId)
    {
        List<RadarInstance> retVal = new ArrayList<RadarInstance>();

        RadarUser foundTeam = this.radarUserRepository.findOne(radarUserId);

        if(foundTeam!=null)
        {
            retVal = this.radarInstanceRepository.findAllByRadarUser(foundTeam.getId());
        }

        return retVal;
    }

    public Technology findTechnologyById(Long technologyId)
    {
        return this.technologyRepository.findOne(technologyId);
    }

    public List<RadarInstance> getAllByTechnologyId(Long technologyId)
    {
        List<RadarInstance> retVal = new ArrayList<RadarInstance>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.radarInstanceRepository.findAllByTechnologyId(foundItem.getId());
        }

        return retVal;
    }

    public RadarInstance addRadarUserAssessment(Long radarUserId, String name)
    {
        RadarInstance retVal = null;

        if(!name.isEmpty())
        {
            RadarInstance existingTeamAssessment = this.radarInstanceRepository.findByIdAndName(radarUserId, name);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(existingTeamAssessment == null && radarUser != null)
            {
                retVal = this.createDefault(radarUser);
                retVal.setName(name);
                this.radarInstanceRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarInstance updateUserRadar(Long radarUserId, Long radarId, String name)
    {
        RadarInstance retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.radarInstanceRepository.findOne(radarId);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUserId)
            {
                retVal.setName(name);
                this.radarInstanceRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarInstance addRadarItem(RadarUser radarUser, Long radarId, String technologyName, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarInstance retVal = null;

        if(!technologyName.isEmpty())
        {
            Technology targetTechnology = this.technologyRepository.findByName(technologyName);

            if (targetTechnology == null)
            {
                targetTechnology = new Technology();
                targetTechnology.setName(technologyName);
                targetTechnology.setCreateDate(new Date());
                targetTechnology.setUrl(technologyUrl);
                targetTechnology.setCreator("");
                targetTechnology.setRadarCategory(this.radarCategoryRepository.findOne(radarCategoryId));
            }

            retVal = this.addRadarItem(radarUser, radarId, targetTechnology, radarRingId, confidenceLevel, assessmentDetails);
        }

        return retVal;
    }

    public RadarInstance addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarInstance retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.radarInstanceRepository.findOne(radarId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
            {
                boolean alreadyIncluded = false;

                for(int i = 0; i < retVal.getRadarInstanceItems().size(); i++)
                {
                    if(retVal.getRadarInstanceItems().get(i).getTechnology().getId()==targetTechnology.getId())
                    {
                        alreadyIncluded = true;
                        break;
                    }
                }

                if(alreadyIncluded == false)
                {
                    RadarInstanceItem newItem = new RadarInstanceItem();
                    newItem.setDetails(assessmentDetails);
                    newItem.setRadarRing(this.radarRingRepository.findOne(radarRingId));
                    newItem.setTechnology(targetTechnology);
                    newItem.setConfidenceFactor(confidenceLevel);
                    retVal.getRadarInstanceItems().add(newItem);
                }

                this.radarInstanceRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarInstanceItem updateRadarItem(Long radarId, Long radarItemId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarInstanceItem retVal = null;

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0)
        {
            RadarInstance assessment = this.radarInstanceRepository.findOne(radarId);

            if(assessment != null)
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                assessment.updateRadarItem(radarItemId, radarRing, confidenceLevel, assessmentDetails);
                this.radarInstanceRepository.save(assessment);
            }
        }
        return retVal;
    }

    public boolean deleteRadarItem(Long radarId, Long radarItemId, Long radarUserId){
        boolean retVal = false;

        RadarInstance radar = this.radarInstanceRepository.findByIdAndRadarUserId(radarId, radarUserId);

        if(radar!=null)
        {
            radar.removeRadarItem(radarItemId);
            this.radarInstanceRepository.save(radar);
            retVal = true;
        }
        return retVal;
    }
}
