package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface RadarInstanceDAO extends PagingAndSortingRepository<RadarInstanceEntity, Long>
{
    Iterable<RadarInstanceEntity> findAllByRadarUserId(Long radarUserId);
    RadarInstanceEntity findByRadarUserAndAssessmentDate(Long radarUserId, Date assessmentDate);
    RadarInstanceEntity findByName(String name);
    RadarInstanceEntity findByIdAndName(Long radarInstanceId, String name);
    RadarInstanceEntity findByIdAndRadarUserId(Long radarInstanceId, Long radarUserId);
}
