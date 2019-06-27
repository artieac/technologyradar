package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

import java.util.List;

public interface IRadarTypeService
{
    List<RadarType> findAllByUserId(RadarUser currentUser, Long userId);
    List<RadarType> findAllByUserAndRadarType(RadarUser currentUser, Long userId, Long radarTypeId);
}
