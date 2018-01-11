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
public class RadarService
{
    private TechnologyRepository technologyRepository;
    private RadarRepository radarRepository;
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public RadarService(RadarRepository radarRepository,
                        TechnologyRepository technologyRepository,
                        RadarRingRepository radarRingRepository,
                        RadarCategoryRepository radarCategoryRepository,
                        RadarUserRepository radarUserRepository)
    {
        this.radarRepository = radarRepository;
        this.technologyRepository = technologyRepository;
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public Radar createDefault(RadarUser radarUser)
    {
        Radar retVal = new Radar();
        retVal.setName("New");
        retVal.setAssessmentDate(new Date());
        retVal.setRadarUser(radarUser);
        retVal.setRadarItems(new ArrayList<RadarItem>());
        return retVal;
    }

    public Radar findById(Long radar)
    {
        return this.radarRepository.findOne(radar);
    }

    public List<Radar> findByRadarUserId(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        RadarUser foundUser = this.radarUserRepository.findOne(radarUserId);

        if(foundUser!=null)
        {
            retVal = this.radarRepository.findAllByRadarUser(foundUser.getId());
        }

        return retVal;
    }

    public Technology findTechnologyById(Long technologyId)
    {
        return this.technologyRepository.findOne(technologyId);
    }

    public List<Radar> getAllByTechnologyId(Long technologyId)
    {
        List<Radar> retVal = new ArrayList<Radar>();

        Technology foundItem = this.technologyRepository.findOne(technologyId);

        if(foundItem!=null)
        {
            retVal = this.radarRepository.findAllByTechnologyId(foundItem.getId());
        }

        return retVal;
    }

    public Radar addRadar(Long radarUserId, String name)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            Radar radarInstance = this.radarRepository.findByIdAndName(radarUserId, name);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(radarInstance == null && radarUser != null)
            {
                retVal = this.createDefault(radarUser);
                retVal.setName(name);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public boolean deleteRadar(Long radarUserId, Long radarInstanceId)
    {
        boolean retVal = false;

        Radar radarInstance = this.radarRepository.findByIdAndRadarUserId(radarInstanceId, radarUserId);

        if(radarInstance!=null)
        {
            this.radarRepository.delete(radarInstance.getId());
            retVal = true;
        }

        return retVal;
    }

    public Radar updateRadar(Long radarUserId, Long radarId, String name)
    {
        Radar retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.radarRepository.findOne(radarId);
            RadarUser radarUser = this.radarUserRepository.findOne(radarUserId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUserId)
            {
                retVal.setName(name);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public Radar addRadarItem(RadarUser radarUser, Long radarId, String technologyName, String technologyUrl, Long radarCategoryId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

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

    public Radar addRadarItem(RadarUser radarUser, Long radarId, Technology targetTechnology, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        Radar retVal = null;

        if(targetTechnology!=null)
        {
            retVal = this.radarRepository.findOne(radarId);

            if(retVal != null && radarUser != null && retVal.getRadarUser().getId() == radarUser.getId())
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                retVal.addRadarItem(targetTechnology, radarRing, confidenceLevel, assessmentDetails);
                this.radarRepository.save(retVal);
            }
        }

        return retVal;
    }

    public RadarItem updateRadarItem(Long radarId, Long radarItemId, Long radarRingId, Integer confidenceLevel, String assessmentDetails)
    {
        RadarItem retVal = null;

        if(radarId > 0 && radarItemId > 0 && radarRingId > 0)
        {
            Radar assessment = this.radarRepository.findOne(radarId);

            if(assessment != null)
            {
                RadarRing radarRing = this.radarRingRepository.findOne(radarRingId);
                assessment.updateRadarItem(radarItemId, radarRing, confidenceLevel, assessmentDetails);
                this.radarRepository.save(assessment);
            }
        }
        return retVal;
    }

    public boolean deleteRadarItem(Long radarId, Long radarItemId, Long radarUserId){
        boolean retVal = false;

        Radar radar = this.radarRepository.findByIdAndRadarUserId(radarId, radarUserId);

        if(radar!=null)
        {
            radar.removeRadarItem(radarItemId);
            this.radarRepository.save(radar);
            retVal = true;
        }
        return retVal;
    }
}
