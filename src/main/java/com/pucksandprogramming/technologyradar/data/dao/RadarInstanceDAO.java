package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarInstanceEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceService;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface RadarInstanceDAO extends PagingAndSortingRepository<RadarInstanceEntity, Long>
{
    List<RadarInstanceEntity> findAllByRadarUserId(Long radarUserId);
    List<RadarInstanceEntity> findAllByRadarUserIdAndIsPublished(Long radarUserId, boolean isPublished);

    RadarInstanceEntity findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId);
    RadarInstanceEntity findByIdAndRadarUserIdAndIsPublished(Long id, Long radarUserId, boolean isPublished);

    RadarInstanceEntity findByRadarUserAndAssessmentDate(Long radarUserId, Date assessmentDate);
    RadarInstanceEntity findByIdAndName(Long radarInstanceId, String name);
}
