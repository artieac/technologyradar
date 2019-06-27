package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarTypeEntity;
import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RadarTypeDAO extends JpaRepository<RadarTypeEntity, VersionedIdEntity>
{
    List<RadarTypeEntity> findAllByRadarUserIdOrderByVersionedId(Long radarUserId);
}