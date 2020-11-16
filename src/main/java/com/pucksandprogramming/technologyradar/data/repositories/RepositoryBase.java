package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by acorrea on 10/26/2016.
 */
@Component
public class RepositoryBase<DomainModel> {
    @Autowired
    protected RadarMapper modelMapper;

    protected Class<DomainModel> domainModelClass;

    public RepositoryBase() {}
    public RepositoryBase(Class<DomainModel> domainModelClass)
    {
        this.domainModelClass = domainModelClass;
    }
}
