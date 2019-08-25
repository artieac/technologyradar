package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarCategorySetEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadarCategorySetDAO extends PagingAndSortingRepository<RadarCategorySetEntity, Long>
{
    List<RadarCategorySetEntity> findByRadarUserId(Long userId);
}
