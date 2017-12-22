package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.RadarCategoryRepository;
import com.alwaysmoveforward.technologyradar.data.repositories.RadarStateRepository;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Component
public class DiagramConfigurationService
{
    private RadarStateRepository radarStateRepository;
    private RadarCategoryRepository radarCategoryRepository;

    @Autowired
    public DiagramConfigurationService(RadarStateRepository radarStateRepository, RadarCategoryRepository radarCategoryRepository)
    {
        this.radarStateRepository = radarStateRepository;
        this.radarCategoryRepository = radarCategoryRepository;
    }

    public List<RadarState> getRadarStates()
    {
        return this.radarStateRepository.GetAllOrdered();
    }

    public List<RadarCategory> getRadarCategories()
    {
        return this.radarCategoryRepository.findAll();
    }
}
