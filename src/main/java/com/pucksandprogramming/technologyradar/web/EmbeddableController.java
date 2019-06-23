package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.services.RadarInstanceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping("/public/embeddable")
public class EmbeddableController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    RadarInstanceService radarInstanceService;

    @RequestMapping(value = { "/user/{userId}/radartype/{radarTypeId}/radars"})
    public ModelAndView mostRecentRadarByType(@PathVariable Long userId,
                                              @PathVariable Long radarTypeId,
                                              @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)

    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTypeId", radarTypeId);

        if(mostRecent==true)
        {
            Radar radarInstance = this.radarInstanceService.findMostRecentByUserIdRadarTypeAndPublished(userId, radarTypeId, true);

            if (radarInstance != null)
            {
                modelAndView.addObject("radarInstanceId", radarInstance.getId());
            }
        }

        modelAndView.setViewName("embeddable/radar");

        return modelAndView;
    }

    @RequestMapping(value = { "/user/{userId}/radars/mostrecent",  "/public/home/user/{userId}/radars"})
    public ModelAndView mostRecentRadar(@PathVariable Long userId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        Radar radarInstance = this.radarInstanceService.findMostRecentByUserIdAndPublished(userId, true);

        if(radarInstance != null)
        {
            modelAndView.addObject("radarInstanceId", radarInstance.getId());
            modelAndView.addObject("radarTypeId", radarInstance.getRadarType().getId());
        }

        modelAndView.setViewName("embeddable/radar");

        return modelAndView;
    }

}
