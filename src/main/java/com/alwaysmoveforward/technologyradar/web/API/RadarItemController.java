package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.services.DiagramConfigurationService;
import com.alwaysmoveforward.technologyradar.services.RadarItemToBeAdded;
import com.alwaysmoveforward.technologyradar.services.RadarService;
import com.alwaysmoveforward.technologyradar.web.ControllerBase;
import com.alwaysmoveforward.technologyradar.web.Models.DiagramPresentation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acorrea on 1/10/2018.
 */
@Controller
@RequestMapping("/api")
public class RadarItemController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(RadarItemController.class);

    @Autowired
    RadarService radarService;

    @Autowired
    DiagramConfigurationService diagramConfigurationService;

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteRadarItem(@PathVariable Long radarId, @PathVariable Long radarItemId, @PathVariable Long radarUserId)
    {
        return this.radarService.deleteRadarItem(radarId, radarItemId, radarUserId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Items/Delete", method = RequestMethod.POST)
    public @ResponseBody List<Long> deleteRadarItems(@RequestBody Map modelMap, @PathVariable Long radarId, @PathVariable Long radarUserId)
    {
        List<Object> mapItems = (List<Object>)modelMap.get("radarItems");

        List<Long> itemsToRemove = new ArrayList<Long>();

        if(mapItems!=null)
        {
            for(int i = 0; i < mapItems.size(); i++)
            {
                itemsToRemove.add(Long.parseLong(mapItems.get(i).toString()));
            }

            this.radarService.deleteRadarItems(radarUserId, radarId, itemsToRemove);
        }

        return itemsToRemove;
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        RadarUser radarUser = radarUserService.findOne(radarUserId);

        if(radarUser != null && radarUser.getId() == this.getCurrentUser().getId())
        {
            Technology targetTechnology = null;

            if (modelMap.get("technologyId") != null)
            {
                List<RadarItemToBeAdded> itemsToAdd = new ArrayList<RadarItemToBeAdded>();

                RadarItemToBeAdded newItem = new RadarItemToBeAdded(Long.parseLong(modelMap.get("radarRing").toString()),
                                                                    Long.parseLong(modelMap.get("technologyId").toString()),
                                                                    modelMap.get("assessmentDetails").toString(),
                                                                    Integer.parseInt(modelMap.get("confidenceLevel").toString()));

                itemsToAdd.add(newItem);

                this.radarService.addRadarItems(this.getCurrentUser(), radarId, itemsToAdd);
            }
            else
            {
                String technologyName = modelMap.get("technologyName").toString();
                Long radarCategory = Long.parseLong(modelMap.get("radarCategory").toString());
                String technologyUrl = modelMap.get("url").toString();

                this.radarService.addRadarItem(this.getCurrentUser(), radarId, technologyName, technologyUrl, radarCategory, radarRing, confidenceLevel, assessmentDetails);
            }
        }

        return this.diagramConfigurationService.generateDiagramData(this.getCurrentUser(), radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Items", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation addRadarItems(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId)
    {
        RadarUser radarUser = radarUserService.findOne(radarUserId);

        if(radarUser != null && radarUser.getId() == this.getCurrentUser().getId())
        {
            List<LinkedHashMap> requestParameters = (List<LinkedHashMap>)modelMap.get("radarItems");

            if(requestParameters != null)
            {
                List<RadarItemToBeAdded> itemsToAdd = new ArrayList<RadarItemToBeAdded>();

                for (int i = 0; i < requestParameters.size(); i++)
                {
                    RadarItemToBeAdded newItem = new RadarItemToBeAdded(Long.parseLong(requestParameters.get(i).get("radarRing").toString()),
                                                                        Long.parseLong(requestParameters.get(i).get("technologyId").toString()),
                                                                        requestParameters.get(i).get("assessmentDetails").toString(),
                                                                        Integer.parseInt(requestParameters.get(i).get("confidenceLevel").toString()));
                    itemsToAdd.add(newItem);
                }

                this.radarService.addRadarItems(this.getCurrentUser(), radarId, itemsToAdd);
            }
        }

        return this.diagramConfigurationService.generateDiagramData(this.getCurrentUser(), radarId);
    }

    @RequestMapping(value = "/User/{radarUserId}/Radar/{radarId}/Item/{radarItemId}", method = RequestMethod.POST)
    public @ResponseBody DiagramPresentation updateRadarItem(@RequestBody Map modelMap, @PathVariable Long radarUserId, @PathVariable Long radarId, @PathVariable Long radarItemId)
    {
        Long radarRing = Long.parseLong(modelMap.get("radarRing").toString());
        Integer confidenceLevel = Integer.parseInt(modelMap.get("confidenceLevel").toString());
        String assessmentDetails = modelMap.get("assessmentDetails").toString();

        if(radarId > 0 && radarItemId > 0 && this.getCurrentUser().getId() == radarUserId);
        {
            this.radarService.updateRadarItem(radarId, radarItemId, radarRing, confidenceLevel, assessmentDetails);
        }

        return this.diagramConfigurationService.generateDiagramData(this.getCurrentUser(), radarId);
    }


}
