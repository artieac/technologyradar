package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarCategoryRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRingRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import com.pucksandprogramming.technologyradar.web.Models.Quadrant;
import com.pucksandprogramming.technologyradar.web.Models.RadarRingPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Component
public class DiagramConfigurationService {
    private static final Logger logger = Logger.getLogger(DiagramConfigurationService.class);

    private RadarRingRepository radarRingRepository;
    private RadarCategoryRepository radarCategoryRepository;
    private RadarUserRepository radarUserRepository;

    @Autowired
    public DiagramConfigurationService(RadarRingRepository radarRingRepository,
                                       RadarCategoryRepository radarCategoryRepository,
                                       RadarUserRepository radarUserRepository) {
        this.radarRingRepository = radarRingRepository;
        this.radarCategoryRepository = radarCategoryRepository;
        this.radarUserRepository = radarUserRepository;
    }

    public List<RadarRing> getRadarRings()
    {
        return this.radarRingRepository.GetAllOrdered();
    }

    public List<RadarCategory> getRadarCategories()
    {
        return this.radarCategoryRepository.findAll();
    }

    public DiagramPresentation generateDiagramData(Long radarUserId, Radar radarInstance) {
        RadarUser targetUser = this.radarUserRepository.findOne(radarUserId);

        return this.generateDiagramData(targetUser, radarInstance);
    }

    public DiagramPresentation generateDiagramData(RadarUser radarUser, Radar radarInstance) {
        DiagramPresentation retVal = new DiagramPresentation();

        retVal.addRadarArcs(radarInstance.getRadarTemplate().getRadarRings());
        Hashtable<Long, RadarRingPresentation> radarRingLookup = new Hashtable<Long, RadarRingPresentation>();
        List<RadarRingPresentation> radarRingPresentations = retVal.getRadarArcs();

        for(RadarRingPresentation radarRing : radarRingPresentations) {
            radarRingLookup.put(radarRing.getRadarRing().getId(), radarRing);
        }

        Hashtable<Long, Quadrant> quadrantLookup = new Hashtable<Long, Quadrant>();

        Integer quadrantStart = 0;
        Integer quadrantSize = 360 / radarInstance.getRadarTemplate().getRadarCategories().size();

        for(RadarCategory radarCategory : radarInstance.getRadarTemplate().getRadarCategories()) {
            Quadrant newQuadrant = new Quadrant(quadrantStart, quadrantSize, radarCategory, retVal.getWidth(), retVal.getHeight(), retVal.getMarginTop(), retVal.getMarginLeft(), radarRingPresentations);
            quadrantLookup.put(radarCategory.getId(), newQuadrant);
            retVal.getQuadrants().add(newQuadrant);
            quadrantStart += quadrantSize;
        }

        if(radarInstance != null) {
            retVal.setRadarInstanceDetails(radarInstance);

            logger.debug(radarInstance);
            logger.debug(radarInstance.getName());
            logger.debug(radarInstance.getRadarItems());
            logger.debug(radarInstance.getRadarItems().size());

            if (radarInstance.getRadarItems().size() > 0) {
                for (RadarItem assessmentItem : radarInstance.getRadarItems()) {
                    Quadrant targetQuadrant = quadrantLookup.get(assessmentItem.getRadarCategory().getId());

                    if (targetQuadrant != null) {
                        targetQuadrant.addItem(radarRingLookup.get(assessmentItem.getRadarRing().getId()), assessmentItem);
                    }
                }
            }

            for (int i = 0; i < retVal.getQuadrants().size(); i++) {
                retVal.getQuadrants().get(i).evenlyDistributeItems();
            }
        }

        return retVal;
    }
}
