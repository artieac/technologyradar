package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/public/embeddable")
public class EmbeddableController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    RadarInstanceServiceFactory radarInstanceServiceFactory;

    @RequestMapping(value = { "/user/{userId}/radartype/{radarTypeId}/radars"})
    public ModelAndView mostRecentRadarByType(@PathVariable Long userId,
                                              @PathVariable String radarTypeId,
                                              @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)

    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTypeId", radarTypeId);

        if(mostRecent==true)
        {
            List<Radar> radarInstance = this.radarInstanceServiceFactory.getMostRecent().findByUserAndType(userId, radarTypeId, true);

            if (radarInstance != null && radarInstance.size() > 0)
            {
                modelAndView.addObject("radarInstanceId", radarInstance.get(0).getId());
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

        List<Radar> radarInstance = this.radarInstanceServiceFactory.getMostRecent().findByRadarUserId(userId, true);

        if(radarInstance != null && radarInstance.size() > 0)
        {
            modelAndView.addObject("radarInstanceId", radarInstance.get(0).getId());
            modelAndView.addObject("radarTypeId", radarInstance.get(0).getRadarType().getId());
        }

        modelAndView.setViewName("embeddable/radar");

        return modelAndView;
    }

}
