package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.PublicRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRepositoryBase;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RadarRepositoryFactory {
    private final RadarAccessManager radarAccessManager;
    private final PublicRadarRepository publicRadarRepository;
    private final FullRadarRepository fullRadarRepository;

    @Autowired
    public RadarRepositoryFactory(RadarAccessManager radarAccessManager, PublicRadarRepository publicRadarRepository, FullRadarRepository fullRadarRepository) {
        this.radarAccessManager = radarAccessManager;
        this.publicRadarRepository = publicRadarRepository;
        this.fullRadarRepository = fullRadarRepository;
    }

    public RadarRepositoryBase getRadarRepository() {
        return this.getRadarRepository(Optional.empty());
    }

    public RadarRepositoryBase getRadarRepository(Optional<RadarUser> targetDataOwner) {
        RadarRepositoryBase retVal = this.publicRadarRepository;

        if(targetDataOwner.isPresent()) {
            RadarAccessManager.ViewAccessMode viewMode = this.radarAccessManager.canViewHistory(targetDataOwner.get());

            switch (viewMode) {
                case FullAccess:
                    retVal = this.fullRadarRepository;
                    break;
                default:
                    retVal = this.publicRadarRepository;
            }
        }

        return retVal;
    }
}
