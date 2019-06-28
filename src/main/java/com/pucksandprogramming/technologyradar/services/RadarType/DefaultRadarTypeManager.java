package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@Component
public class DefaultRadarTypeManager
{
    private static List<RadarType> defaultRadars;

    public static List<RadarType> getDefaultRadarTypes(RadarTypeServiceBase radarTypeService)
    {
        if (DefaultRadarTypeManager.defaultRadars == null)
        {
            DefaultRadarTypeManager.defaultRadars = new ArrayList<RadarType>();

            DefaultRadarTypeManager radarTypeManager = new DefaultRadarTypeManager(radarTypeService);
            DefaultRadarTypeManager.defaultRadars = radarTypeManager.findDefaultRadarTypes();
        }

        return DefaultRadarTypeManager.defaultRadars;
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

//    @Value("${com.pucksandprogramming.defaultRadarIds}")
    private  String defaultRadarIds;

    RadarTypeServiceBase radarTypeService;

    public DefaultRadarTypeManager(RadarTypeServiceBase radarTypeService)
    {
        this.radarTypeService = radarTypeService;
    }

    public List<RadarType> findDefaultRadarTypes()
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        retVal.add(this.radarTypeService.findOne(1L,1L));
        retVal.add(this.radarTypeService.findOne(2L,1L));

        return retVal;
    }
}
