package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingSetEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadarRingSetDAO extends PagingAndSortingRepository<RadarRingSetEntity, Long>
{
    List<RadarRingSetEntity> findByRadarUserId(Long userId);
}
