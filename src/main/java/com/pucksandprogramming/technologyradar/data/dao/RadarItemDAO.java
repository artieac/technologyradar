package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by acorrea on 10/28/2016.
 */
public interface RadarItemDAO extends PagingAndSortingRepository<RadarItemEntity, Long>
{
}
