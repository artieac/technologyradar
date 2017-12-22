package com.alwaysmoveforward.technologyradar.data.mapper;

import com.alwaysmoveforward.technologyradar.data.dto.*;
import com.alwaysmoveforward.technologyradar.domainmodel.*;
import com.alwaysmoveforward.technologyradar.data.dto.*;
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


    private PropertyMap<RadarStateDTO, RadarState> radarConfigModelMap =
            new PropertyMap<RadarStateDTO, RadarState>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<RadarCategoryDTO, RadarCategory> radarCategoryModelMap =
            new PropertyMap<RadarCategoryDTO, RadarCategory>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<AssessmentTeamDTO, AssessmentTeam> assessmentTeamMap =
            new PropertyMap<AssessmentTeamDTO, AssessmentTeam>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };
    /**
     * Custom map from {@link TechnologyDTO} to the
     * {@link Technology}
     */
    private PropertyMap<TechnologyDTO, Technology> technologyConfigModelMap =
            new PropertyMap<TechnologyDTO, Technology>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

    private PropertyMap<TechnologyAssessmentDTO, TechnologyAssessment> technologyAssessmentMap =
            new PropertyMap<TechnologyAssessmentDTO, TechnologyAssessment>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };


    private PropertyMap<TechnologyAssessmentItemDTO, TechnologyAssessmentItem> technologyAssessmentItemMap =
            new PropertyMap<TechnologyAssessmentItemDTO, TechnologyAssessmentItem>()
            {
                protected void configure()
                {
                    map().setId(source.getId());
                }
            };

}

