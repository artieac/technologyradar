package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarInstanceRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarTypeRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostRecentRadarTypesService implements IRadarTypeService
{
    private RadarTypeRepository radarTypeRepository;

    @Autowired
    public MostRecentRadarTypesService(RadarTypeRepository radarTypeRepository)
    {
        this.radarTypeRepository = radarTypeRepository;
    }

    public List<RadarType> findAllByUserId(RadarUser currentUser, Long userId)
    {
        return this.radarTypeRepository.findMostRecentRadarTypesForUser(userId);
    }

    public List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, Long radarTypeId)
    {
        return this.radarTypeRepository.findHistoryForRadarType(userId, radarTypeId);
    }
}
