package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface RadarDAO extends PagingAndSortingRepository<RadarEntity, Long>
{
    Iterable<RadarEntity> findAllByRadarUserId(Long radarUserId);
    Iterable<RadarEntity> findAllByRadarUserIdAndIsPublished(Long radarUserId, boolean isPublished);
    RadarEntity findByRadarUserAndAssessmentDate(Long radarUserId, Date assessmentDate);
    RadarEntity findByName(String name);
    RadarEntity findByIdAndName(Long radarInstanceId, String name);
    RadarEntity findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId);
}
