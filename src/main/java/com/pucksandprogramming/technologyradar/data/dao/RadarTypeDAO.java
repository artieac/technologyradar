package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface RadarTypeDAO extends JpaRepository<RadarTypeEntity, Long>
{
    RadarTypeEntity findByRadarUserIdAndId(Long radarUserId, Long id);
    Iterable<RadarTypeEntity> findAllByRadarUserId(Long radarUserId);
    Iterable<RadarTypeEntity> findAllByIsPublished(boolean isPublished);
}