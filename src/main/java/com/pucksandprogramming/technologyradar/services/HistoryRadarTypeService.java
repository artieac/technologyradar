package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryRadarTypeService implements IRadarTypeService
{
    private RadarTypeRepository radarTypeRepository;

    @Autowired
    public HistoryRadarTypeService(RadarTypeRepository radarTypeRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
    }

    public List<RadarType> findAllByUserId(RadarUser currentUser, Long userId)
    {
        List<RadarType> retVal = new ArrayList<>();

        if(currentUser!=null && currentUser.canSeeHistory()==true)
        {
            retVal = this.radarTypeRepository.findAllRadarTypeVersionsForUser(userId);
        }

        return retVal;
    }

    public List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, Long radarTypeId)
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        if(currentUser!=null && currentUser.canSeeHistory()==true)
        {
            retVal = this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
        }

        return retVal;
    }
}
