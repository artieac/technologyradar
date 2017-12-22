package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.dto.TechnologyAssessmentDTO;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by acorrea on 10/21/2016.
 */
public interface TechnologyAssessmentDAO  extends PagingAndSortingRepository<TechnologyAssessmentDTO, Long>
{
    Iterable<TechnologyAssessmentDTO> findAllByAssessmentTeamId(Long teamId);
    TechnologyAssessmentDTO findByAssessmentTeamAndAssessmentDate(Long teamId, Date assessmentDate);
    TechnologyAssessmentDTO findByName(String name);
}
