package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarRingSetEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarTemplateEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadarTemplateDAO extends PagingAndSortingRepository<RadarTemplateEntity, Long>
{
    List<RadarTemplateEntity> findByRadarUserId(Long userId);
}
