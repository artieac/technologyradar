package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyAssessmentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface TechnologyAssessmentDAO  extends PagingAndSortingRepository<TechnologyAssessmentEntity, Long>
{
    Iterable<TechnologyAssessmentEntity> findAllByRadarUserId(Long radarUserId);
    TechnologyAssessmentEntity findByAssessmentRadarUserAndAssessmentDate(Long radarUserId, Date assessmentDate);
    TechnologyAssessmentEntity findByName(String name);
    TechnologyAssessmentEntity findByIdAndName(Long id, String name);
}
