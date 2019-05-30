package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategoryEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RadarTypeDAO extends PagingAndSortingRepository<RadarTypeEntity, Long>
{
    Iterable<RadarTypeEntity> findAllByRadarUserId(Long radarUserId);
}