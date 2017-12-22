package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.dto.RadarStateDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public interface RadarStateDAO extends PagingAndSortingRepository<RadarStateDTO, Long>
{

}
