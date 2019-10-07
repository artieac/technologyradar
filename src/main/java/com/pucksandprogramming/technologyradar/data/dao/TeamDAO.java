package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.TeamEntity;
import com.pucksandprogramming.technologyradar.data.Entities.UserTypeEntity;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamDAO extends PagingAndSortingRepository<TeamEntity, Long>
{

}
