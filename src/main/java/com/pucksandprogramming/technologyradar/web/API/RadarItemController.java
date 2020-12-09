package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.services.DiagramConfigurationService;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarItemToBeAdded;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;

/**
 * Created by acorrea on 1/10/2018.
 */
@Controller
@RequestScope
@RequestMapping("/api")
public class RadarItemController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RadarItemController.class);

    private final RadarService radarService;
    private final DiagramConfigurationService diagramConfigurationService;
    private final RadarUserService radarUserService;

    @Autowired
    public RadarItemController(RadarService radarService,
                               DiagramConfigurationService _diagramConfigurationService,
                               RadarUserService _radarUserService) {
        this.radarService = radarService;
        this.diagramConfigurationService = _diagramConfigurationService;
        this.radarUserService = _radarUserService;
    }

    @DeleteMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}")
    public @ResponseBody boolean deleteRadarItem(@PathVariable Long radarId, @PathVariable Long radarItemId, @PathVariable Long radarUserId) {
        boolean retVal = false;

        try {
            retVal = this.radarService.deleteRadarItem(radarId, radarItemId, radarUserId);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar/{radarId}/Items/Delete")
    public @ResponseBody List<Long> deleteRadarItems(@RequestBody Map modelMap, @PathVariable Long radarId, @PathVariable Long radarUserId) {
        List<Long> retVal = new ArrayList<>();

        try {
            List<Object> mapItems = (List<Object>) modelMap.get("radarItems");

            if (mapItems != null) {
                for (int i = 0; i < mapItems.size(); i++) {
                    retVal.add(Long.parseLong(mapItems.get(i).toString()));
                }

                this.radarService.deleteRadarItems(radarUserId, radarId, retVal);
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item")
    public @ResponseBody
    DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId) {
        DiagramPresentation retVal = new DiagramPresentation();

        try {
            Long radarCategoryId = Long.parseLong(modelMap.get("radarCategory").toString());
            Long radarRingId = Long.parseLong(modelMap.get("radarRing").toString());
            Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
            String assessmentDetails = modelMap.get("assessmentDetails").toString();

            if(this.isCurrentUser(radarUserId)) {
                if (modelMap.get("technologyId") != null) {
                    List<RadarItemToBeAdded> itemsToAdd = new ArrayList<RadarItemToBeAdded>();

                    RadarItemToBeAdded newItem = new RadarItemToBeAdded(radarCategoryId,
                            radarRingId,
                            Long.parseLong(modelMap.get("technologyId").toString()),
                            modelMap.get("assessmentDetails").toString(),
                            Integer.parseInt(modelMap.get("confidenceLevel").toString()));

                    itemsToAdd.add(newItem);

                    this.radarService.addRadarItems(this.getCurrentUser(), radarId, itemsToAdd);
                }
                else {
                    String technologyName = modelMap.get("technologyName").toString();
                    String technologyUrl = modelMap.get("url").toString();

                    this.radarService.addRadarItem(this.getCurrentUser(), radarId, technologyName, technologyUrl, radarCategoryId, radarRingId, confidenceLevel, assessmentDetails);
                }
            }

            Radar targetRadar = this.radarService.findByUserAndRadarId(this.getCurrentUser().getId(), radarId);
            retVal = this.diagramConfigurationService.generateDiagramData(Optional.ofNullable(this.getCurrentUser()), targetRadar);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar/{radarId}/Items")
    public @ResponseBody DiagramPresentation addRadarItems(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId) {
        DiagramPresentation retVal = new DiagramPresentation();

        try {
            if(this.isCurrentUser(radarUserId)){
                List<LinkedHashMap> requestParameters = (List<LinkedHashMap>) modelMap.get("radarItems");

                if (requestParameters != null) {
                    List<RadarItemToBeAdded> itemsToAdd = new ArrayList<RadarItemToBeAdded>();

                    for (int i = 0; i < requestParameters.size(); i++) {
                        RadarItemToBeAdded newItem = new RadarItemToBeAdded(Long.parseLong(requestParameters.get(i).get("radarCategory").toString()),
                                Long.parseLong(requestParameters.get(i).get("radarRing").toString()),
                                Long.parseLong(requestParameters.get(i).get("technologyId").toString()),
                                requestParameters.get(i).get("assessmentDetails").toString(),
                                Integer.parseInt(requestParameters.get(i).get("confidenceLevel").toString()));
                        itemsToAdd.add(newItem);
                    }

                    this.radarService.addRadarItems(this.getCurrentUser(), radarId, itemsToAdd);
                }
            }

            Radar targetRadar = this.radarService.findByUserAndRadarId(this.getCurrentUser().getId(), radarId);
            retVal = this.diagramConfigurationService.generateDiagramData(Optional.ofNullable(this.getCurrentUser()), targetRadar);
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}")
    public @ResponseBody DiagramPresentation updateRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId, @PathVariable Long radarItemId) {
        DiagramPresentation retVal = new DiagramPresentation();

        try {
            Long radarCategory = Long.parseLong(modelMap.get("radarCategory").toString());
            Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
            Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
            String assessmentDetails = modelMap.get("assessmentDetails").toString();

            if (radarId > 0 && radarItemId > 0 && this.getCurrentUser().getId() == radarUserId) ;{
                this.radarService.updateRadarItem(radarId, radarItemId, radarCategory, radarRing, confidenceLevel, assessmentDetails);
            }

            Radar targetRadar = this.radarService.findByUserAndRadarId(this.getCurrentUser().getId(), radarId);
            retVal = this.diagramConfigurationService.generateDiagramData(Optional.ofNullable(this.getCurrentUser()), targetRadar);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return retVal;
    }
}
