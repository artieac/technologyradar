package com.alwaysmoveforward.technologyradar.data.mapper;

import com.alwaysmoveforward.technologyradar.data.Entities.*;
import com.alwaysmoveforward.technologyradar.domainmodel.*;
import org.springframework.stereotype.Component;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.annotation.PostConstruct;

/**
 * Created by acorrea on 10/18/2016.
 */
@Component
public class TechnologyRadarMapper
{

    private ModelMapper modelMapper;

    public TechnologyRadarMapper()
    {
        initializeMapper();
    }

    @PostConstruct
    private void initializeMapper()
    {
        modelMapper = new ModelMapper();

        // do not map null objects
        modelMapper.getConfiguration().setPropertyCondition(
                Conditions.isNotNull());

        // Product Config
        modelMapper.addMappings(radarConfigModelMap);
        modelMapper.addMappings(radarCategoryModelMap);
        modelMapper.addMappings(technologyConfigModelMap);
        modelMapper.addMappings(assessmentTeamMap);
        modelMapper.addMappings(technologyAssessmentMap);
        modelMapper.addMappings(technologyAssessmentItemMap);
        modelMapper.addMappings(radarUserModelMap);
    }

    private ModelMapper getMapper()
    {
        if (modelMapper == null)
        {
            initializeMapper();
        }
        return modelMapper;
    }

    @SuppressWarnings("unused")
    private void setMapper(ModelMapper mapper)
    {
        this.modelMapper = mapper;
    }

    /**
     * Implementation of map to convert one object into another.
     *
     * @param source
     * @param destinationType
     * @return mapped object that matches the expected destinationType
     */
    public <D> D map(Object source, Class<D> destinationType)
    {
        return getMapper().map(source, destinationType);
    }


    private PropertyMap<RadarRingEntity, RadarRing> radarConfigModelMap =
            new PropertyMap<RadarRingEntity, RadarRing>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<RadarCategoryEntity, RadarCategory> radarCategoryModelMap =
            new PropertyMap<RadarCategoryEntity, RadarCategory>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<AssessmentTeamEntity, AssessmentTeam> assessmentTeamMap =
            new PropertyMap<AssessmentTeamEntity, AssessmentTeam>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };
    /**
     * Custom map from {@link TechnologyEntity} to the
     * {@link Technology}
     */
    private PropertyMap<TechnologyEntity, Technology> technologyConfigModelMap =
            new PropertyMap<TechnologyEntity, Technology>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<TechnologyAssessmentEntity, TechnologyAssessment> technologyAssessmentMap =
            new PropertyMap<TechnologyAssessmentEntity, TechnologyAssessment>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };


    private PropertyMap<TechnologyAssessmentItemEntity, TechnologyAssessmentItem> technologyAssessmentItemMap =
            new PropertyMap<TechnologyAssessmentItemEntity, TechnologyAssessmentItem>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<RadarUserEntity, RadarUser> radarUserModelMap =
            new PropertyMap<RadarUserEntity, RadarUser>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

}

