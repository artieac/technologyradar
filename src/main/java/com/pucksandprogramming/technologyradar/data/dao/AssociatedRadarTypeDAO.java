package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.AssociatedRadarTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociatedRadarTypeDAO extends JpaRepository<AssociatedRadarTypeEntity, Long> {
    AssociatedRadarTypeEntity findByRadarUserIdAndRadarTypeId(Long radarUserId, Long radarTypeId);
}
