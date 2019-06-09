package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${com.pucksandprogramming.defaultRadarIds}")
    private  String defaultRadarIds;

    RadarTypeService radarTypeService;

    public DefaultRadarTypeManager(RadarTypeService radarTypeService)
    {
        this.radarTypeService = radarTypeService;
    }

    public List<RadarType> findDefaultRadarTypes()
    {
        List<RadarType> retVal = new ArrayList<RadarType>();

        // for now get this from config, eventually shift this to database
        if(this.defaultRadars!=null)
        {
            // until I get the config working...
            if(this.defaultRadarIds==null)
            {
                this.defaultRadarIds = "1,3";
            }

            String[] radarIds = this.defaultRadarIds.split(",");

            for(String idString : radarIds)
            {
                Long idValue = Long.parseLong(idString);

                RadarType foundRadar = this.radarTypeService.findOne(idValue);

                if(foundRadar!=null)
                {
                    retVal.add(foundRadar);
                }
            }
        }

        return retVal;
    }
}
