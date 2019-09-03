package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by acorrea on 12/23/2017.
 */
@Repository
public interface RadarUserDAO extends PagingAndSortingRepository<RadarUserEntity, Long>
{
        RadarUserEntity findByAuthenticationId(String authenticationId);
        List<RadarUserEntity> findByEmail(String email);
        List<RadarUserEntity> findByEmailContaining(String email);
}

