package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.dto.AssessmentTeamDTO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface AssessmentTeamDAO extends PagingAndSortingRepository<AssessmentTeamDTO, Long>
{
    AssessmentTeamDTO findByName(String name);
}
