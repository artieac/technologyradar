package com.alwaysmoveforward.technologyradar.data.dao;

import com.alwaysmoveforward.technologyradar.data.Entities.RadarInstanceItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by acorrea on 10/28/2016.
 */
public interface RadarInstanceItemDAO extends PagingAndSortingRepository<RadarInstanceItemEntity, Long>
{
}