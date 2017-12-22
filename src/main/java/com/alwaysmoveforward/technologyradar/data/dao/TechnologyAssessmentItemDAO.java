package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.dto.TechnologyAssessmentItemDTO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by acorrea on 10/28/2016.
 */
public interface TechnologyAssessmentItemDAO extends PagingAndSortingRepository<TechnologyAssessmentItemDTO, Long>
{
}
