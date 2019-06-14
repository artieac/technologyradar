package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarInstanceEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface RadarInstanceDAO extends PagingAndSortingRepository<RadarInstanceEntity, Long>
{
    Iterable<RadarInstanceEntity> findAllByRadarUserId(Long radarUserId);
    Iterable<RadarInstanceEntity> findAllByRadarUserIdAndIsPublished(Long radarUserId, boolean isPublished);
    RadarInstanceEntity findByRadarUserAndAssessmentDate(Long radarUserId, Date assessmentDate);
    RadarInstanceEntity findByIdAndName(Long radarInstanceId, String name);
    RadarInstanceEntity findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId);
    Iterable<RadarInstanceEntity> findAllByRadarUserIdAndRadarTypeIdAndIsPublished(Long radarUserId, Long radarTypeId, boolean isPublished);
    Iterable<RadarInstanceEntity> findAllByRadarUserIdAndRadarTypeId(Long radarUserId, Long radarTypeId);

}
