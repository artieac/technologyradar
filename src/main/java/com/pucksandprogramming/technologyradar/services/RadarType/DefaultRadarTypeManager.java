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

        retVal.add(this.radarTypeService.findOne("e9b8779a-3452-47b9-ab49-600162eace3c",1L));
        retVal.add(this.radarTypeService.findOne("a2330b55-164a-49a0-a883-56d46c34a399",1L));

        return retVal;
    }
}
