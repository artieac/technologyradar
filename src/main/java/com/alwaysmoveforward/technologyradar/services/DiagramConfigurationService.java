package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.RadarCategoryRepository;
import com.alwaysmoveforward.technologyradar.data.repositories.RadarRingRepository;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarCategory;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Component
public class DiagramConfigurationService
{
    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;

    @Autowired
    public DiagramConfigurationService(RadarRingRepository radarRingRepository, RadarCategoryRepository radarCategoryRepository)
    {
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
    }

    public List<RadarRing> getRadarRings()
    {
        return this.radarRingRepository.GetAllOrdered();
    }

    public List<RadarCategory> getRadarCategories()
    {
        return this.radarCategoryRepository.findAll();
    }
}
