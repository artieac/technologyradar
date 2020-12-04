package com.pucksandprogramming.technologyradar.services.RadarTemplate;

import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Configuration
//@Component
public class DefaultRadarTemplateManager {
    private static List<RadarTemplate> defaultRadars;

    public static List<RadarTemplate> getDefaultRadarTemplates(RadarTemplateService radarTemplateService) {
        if (DefaultRadarTemplateManager.defaultRadars == null) {
            DefaultRadarTemplateManager.defaultRadars = new ArrayList<RadarTemplate>();

            DefaultRadarTemplateManager radarTemplateManager = new DefaultRadarTemplateManager(radarTemplateService);
            DefaultRadarTemplateManager.defaultRadars = radarTemplateManager.findDefaultRadarTemplates();
        }

        return DefaultRadarTemplateManager.defaultRadars;
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

//    @Value("${com.pucksandprogramming.defaultRadarIds}")
    private  String defaultRadarIds;

    RadarTemplateService radarTemplateService;

    public DefaultRadarTemplateManager(RadarTemplateService radarTemplateService) {
        this.radarTemplateService = radarTemplateService;
    }

    public List<RadarTemplate> findDefaultRadarTemplates() {
        List<RadarTemplate> retVal = new ArrayList<RadarTemplate>();

        Optional<RadarTemplate> sharedType = this.radarTemplateService.findOneShared(3L);

        if(sharedType.isPresent()) {
            retVal.add(sharedType.get());
        }

        sharedType = this.radarTemplateService.findOneShared(1L);

        if(sharedType.isPresent()) {
            retVal.add(sharedType.get());
        }

        return retVal;
    }
}
