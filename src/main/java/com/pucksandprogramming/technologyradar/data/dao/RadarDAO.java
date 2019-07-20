package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface RadarDAO extends PagingAndSortingRepository<RadarEntity, Long>
{
    List<RadarEntity> findAllByRadarUserId(Long radarUserId);
    List<RadarEntity> findAllByRadarUserIdAndIsPublished(Long radarUserId, boolean isPublished);

    RadarEntity findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId);

    RadarEntity findByIdAndRadarUserIdAndIsPublished(Long id, Long radarUserId, boolean isPublished);

    RadarEntity findByIdAndName(Long radarInstanceId, String name);
}
