package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.MostRecentRadarComparator;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
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
    RadarService radarService;

    @Autowired
    RadarUserService radarUserService;

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
            Radar targetRadar = this.radarService.findById(radarInstanceId.get());

            if (targetRadar != null)
            {
                modelAndView.addObject("radarInstanceId", radarInstanceId.get());
                modelAndView.addObject("radarTypeId", targetRadar.getRadarType().getId());
                modelAndView.addObject("radarTypeVersion", targetRadar.getRadarType().getVersion());
            }
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    @RequestMapping(value = { "/home/user/{userId}/radars"})
    public ModelAndView viewUserRadars(@PathVariable Long userId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        RadarUser targetDataOwner = this.radarUserService.findOne(userId);

        if(targetDataOwner!=null)
        {
            List<Radar> radarInstances = this.radarService.findByRadarUserId(userId);

            if (radarInstances != null && radarInstances.size() > 0)
            {
                modelAndView.addObject("radarInstanceId", radarInstances.get(0).getId());
                modelAndView.addObject("radarTypeId", radarInstances.get(0).getRadarType().getId());
                modelAndView.addObject("radarTypeVersion", radarInstances.get(0).getRadarType().getVersion());
            }
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

            RadarUser dataOwner = this.radarUserService.findOne(userId);

            // TBD if they can't share with others make sure this is the most recent.
            Radar radarInstance = this.radarService.findByUserAndRadarId(userId, radarInstanceId.get());

            if(radarInstance!=null)
            {
                modelAndView.addObject("radarTypeId", radarInstance.getRadarType().getId());
                modelAndView.addObject("radarTypeVersion", radarInstance.getRadarType().getVersion());
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

        List<Radar> radarInstances = this.radarService.findByRadarUserId(userId);

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
                                                @PathVariable String radarTypeId,
                                                @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)

    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTypeId", radarTypeId);

        if(mostRecent==true)
        {
            List<Radar> radarInstances = this.radarService.findByUserAndType(userId, radarTypeId);

            if (radarInstances != null && radarInstances.size() > 0)
            {
                Radar mostRecentRadar = Collections.max(radarInstances, new MostRecentRadarComparator());

                if (mostRecentRadar != null)
                {
                    modelAndView.addObject("radarTypeVersion", mostRecentRadar.getRadarType().getVersion());
                    modelAndView.addObject("radarInstanceId", mostRecentRadar.getId());
                }
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
