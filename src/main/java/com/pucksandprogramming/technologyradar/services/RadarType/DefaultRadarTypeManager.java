package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@Component
public class DefaultRadarTypeManager
{
    private static List<RadarType> defaultRadars;

    public static List<RadarType> getDefaultRadarTypes(RadarTypeService radarTypeService)
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

    RadarTypeService radarTypeService;

    public DefaultRadarTypeManager(RadarTypeService radarTypeService)
    {
        this.radarTypeService = radarTypeService;
    }

    public List<RadarType> findDefaultRadarTypes()
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        RadarType sharedType = this.radarTypeService.findOneShared(3L);

        if(sharedType!=null)
        {
            retVal.add(sharedType);
        }

        sharedType = this.radarTypeService.findOneShared(1L);

        if(sharedType!=null)
        {
            retVal.add(sharedType);
        }

        return retVal;
    }
}
