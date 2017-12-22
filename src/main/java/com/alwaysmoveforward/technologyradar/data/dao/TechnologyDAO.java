package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.dto.TechnologyDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by acorrea on 10/18/2016.
 */
@Repository
public interface TechnologyDAO extends PagingAndSortingRepository<TechnologyDTO, Long>
{
    TechnologyDTO findByName(String name);
}
