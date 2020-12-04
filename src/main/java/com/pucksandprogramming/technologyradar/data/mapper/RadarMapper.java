package com.pucksandprogramming.technologyradar.data.mapper;

import com.pucksandprogramming.technologyradar.data.Entities.*;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import org.springframework.stereotype.Component;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.annotation.PostConstruct;

/**
 * Created by acorrea on 10/18/2016.
 */
@Component
public class RadarMapper {

    private ModelMapper modelMapper;

    public RadarMapper()
    {
        initializeMapper();
    }

    @PostConstruct
    private void initializeMapper() {
        modelMapper = new ModelMapper();

        // do not map null objects
        modelMapper.getConfiguration().setPropertyCondition(
                Conditions.isNotNull());

        // Product Config
        this.addRadarRingMappings();
        this.addRadarCategoryMappings();
        this.addRadarTemplateMappings();
        this.addTechnologyMappings();
        this.addRadarMappings();
        this.addRadarItemMappings();
        this.addUserTypeMappings();
        this.addRadarUserMappings();
    }

    private ModelMapper getMapper() {
        if (modelMapper == null) {
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

    private void addRadarRingMappings(){
        this.modelMapper.typeMap(RadarRingEntity.class, RadarRing.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), RadarRing::setId);
        });
    }

    private void addRadarCategoryMappings(){
        this.modelMapper.typeMap(RadarCategoryEntity.class, RadarCategory.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), RadarCategory::setId);
        });
    }

    private void addTechnologyMappings(){
        this.modelMapper.typeMap(TechnologyEntity.class, Technology.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), Technology::setId);
        });
    }

    private void addRadarMappings(){
        this.modelMapper.typeMap(RadarEntity.class, Radar.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), Radar::setId);
        });
    }

    private void addRadarItemMappings(){
        this.modelMapper.typeMap(RadarItemEntity.class, RadarItem.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), RadarItem::setId);
        });
    }

    private void addRadarTemplateMappings(){
        this.modelMapper.typeMap(RadarTemplateEntity.class, RadarTemplate.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), RadarTemplate::setId);
        });
    }

    private void addRadarUserMappings(){
        this.modelMapper.typeMap(RadarUserEntity.class, RadarUser.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), RadarUser::setId);
        });
    }

    private void addUserTypeMappings(){
        this.modelMapper.typeMap(UserTypeEntity.class, UserType.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), UserType::setId);
        });
    }
}

