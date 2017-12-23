package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.Entities.AssessmentTeamEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface AssessmentTeamDAO extends PagingAndSortingRepository<AssessmentTeamEntity, Long>
{
    AssessmentTeamEntity findByName(String name);
}
