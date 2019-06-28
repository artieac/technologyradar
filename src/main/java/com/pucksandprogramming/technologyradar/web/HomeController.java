package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarInstanceServiceFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/14/2016.
 */

@Controller
public class HomeController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    RadarInstanceServiceFactory radarInstanceServiceFactory;

    @RequestMapping( value = {"/", "/public/home/index"})
    public String index(Model viewModel)
    {
        return "home/index";
    }

    @RequestMapping(value = { "/home/secureradar", "/home/secureradar/{radarInstanceId}" })
    public ModelAndView secureRadar(@PathVariable Optional<Long> radarInstanceId)
    {
        ModelAndView modelAndView = new ModelAndView();
        RadarUser currentUser = this.getCurrentUser();

        if(currentUser != null)
        {
            modelAndView.addObject("userId", currentUser.getId());
        }

        if(radarInstanceId.isPresent())
        {
            modelAndView.addObject("radarInstanceId", radarInstanceId.get());
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    // I hate this url format, but I can't figure out how to get seccurity working with the
    // format that I want
    @RequestMapping(value = { "/public/home/user/{userId}/radar/{radarInstanceId}" })
    public ModelAndView publicRadar(@PathVariable Long userId, @PathVariable Optional<Long> radarInstanceId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        if(radarInstanceId.isPresent())
        {
            modelAndView.addObject("radarInstanceId", radarInstanceId.get());

            // TBD if they can't share with others make sure this is the most recent.
            Radar radarInstance = radarInstanceServiceFactory.getRadarTypeServiceForSharing().findByUserAndRadarId(userId, radarInstanceId.get(), true);

            if(radarInstance!=null)
            {
                modelAndView.addObject("radarTypeId", radarInstance.getRadarType().getId());
            }
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/user/{userId}/radars/mostrecent",  "/public/home/user/{userId}/radars"})
    public ModelAndView mostRecentRadar(@PathVariable Long userId,
                                        @RequestParam(name="embeddable", required = false, defaultValue="false") Boolean isEmbeddable)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        List<Radar> radarInstances = this.radarInstanceServiceFactory.getMostRecent().findByRadarUserId(userId, true);

        if(radarInstances != null && radarInstances.size() > 0)
        {
            modelAndView.addObject("radarInstanceId", radarInstances.get(0).getId());
            modelAndView.addObject("radarTypeId", radarInstances.get(0).getRadarType().getId());
        }

        if(isEmbeddable)
        {
            modelAndView.setViewName("home/embeddedradar");
        }
        else {
            modelAndView.setViewName("home/radar");
        }

        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/user/{userId}/radartype/{radarTypeId}/radars"})
    public ModelAndView mostRecentRadarByType(  @PathVariable Long userId,
                                                @PathVariable Long radarTypeId,
                                                @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)

    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTypeId", radarTypeId);

        if(mostRecent==true)
        {
            List<Radar> radarInstances = this.radarInstanceServiceFactory.getMostRecent().findByUserAndType(userId, radarTypeId, true);

            if (radarInstances != null && radarInstances.size() > 0)
            {
                modelAndView.addObject("radarInstanceId", radarInstances.get(0).getId());
            }
        }

        modelAndView.setViewName("home/radar");

        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/about", "/public/home/about" })
    public ModelAndView About()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/about");
        return modelAndView;
    }
}
