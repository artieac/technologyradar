package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarTemplateEntity;
import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RadarTemplateDAO extends JpaRepository<RadarTemplateEntity, Long>
{
    List<RadarTemplateEntity> findAllByRadarUserIdAndStateOrderByCreateDate(Long radarUserId, Integer state);
}