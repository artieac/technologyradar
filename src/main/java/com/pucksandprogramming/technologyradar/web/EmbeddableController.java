package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
@CrossOrigin
@RequestMapping("/public/embeddable")
public class EmbeddableController {
    private static final Logger logger = Logger.getLogger(EmbeddableController.class);

    private final RadarService radarService;

    @Autowired
    public EmbeddableController(RadarService radarService){
        this.radarService = radarService;
    }

    @RequestMapping(value = { "/user/{userId}/radartemplate/{radarTemplateId}/radars"})
    public ModelAndView mostRecentRadarByType(@PathVariable Long userId,
                                              @PathVariable Long radarTemplateId,
                                              @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTemplateId", radarTemplateId);

        if(mostRecent==true) {
            List<Radar> radarInstance = this.radarService.findByUserAndType(userId, radarTemplateId);

            if (radarInstance != null && radarInstance.size() > 0) {
                modelAndView.addObject("radarInstanceId", radarInstance.get(0).getId());
            }
        }

        modelAndView.setViewName("embeddable/radar");

        return modelAndView;
    }

    @RequestMapping(value = { "/user/{userId}/radars/mostrecent",  "/public/home/user/{userId}/radars"})
    public ModelAndView mostRecentRadar(@PathVariable Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        List<Radar> radarInstance = this.radarService.findByRadarUserId(userId);

        if(radarInstance != null && radarInstance.size() > 0) {
            modelAndView.addObject("radarInstanceId", radarInstance.get(0).getId());
            modelAndView.addObject("radarTemplateId", radarInstance.get(0).getRadarTemplate().getId());
        }

        modelAndView.setViewName("embeddable/radar");

        return modelAndView;
    }
}
