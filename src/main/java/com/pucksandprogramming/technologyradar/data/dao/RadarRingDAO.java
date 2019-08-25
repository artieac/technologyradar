package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarRingEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarRingSetEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public interface RadarRingDAO extends PagingAndSortingRepository<RadarRingEntity, Long>
{
}
