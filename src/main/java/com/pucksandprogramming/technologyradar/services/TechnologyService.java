package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.TechnologyRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 11/3/2016.
 */
@Component
@RequestScope
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public static Technology createDefaultTechnology() {
        Technology retVal = new Technology();
        retVal.setId(0L);
        retVal.setCreator("None");
        retVal.setName("Nothing");
        retVal.setUrl("http://www.foo.com");
        retVal.setCreateDate(new Date());
        return retVal;
    }

    public Optional<Technology> findById(Long technologyId) {
        return this.technologyRepository.findById(technologyId);
    }

    public List<Technology> searchTechnology(String technologyName, String radarTemplateId, Long radarRingId, Long radarCategoryId) {
        return this.technologyRepository.findByFilters(technologyName, radarTemplateId, radarRingId, radarCategoryId);
    }
}
