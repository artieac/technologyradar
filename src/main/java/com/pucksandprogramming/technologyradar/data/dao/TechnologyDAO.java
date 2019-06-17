package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.TechnologyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by acorrea on 10/18/2016.
 */

@Repository
public interface TechnologyDAO extends PagingAndSortingRepository<TechnologyEntity, Long>
{
    TechnologyEntity findByName(String name);

    List<TechnologyEntity> findByNameStartingWith(String technologyName);
}
