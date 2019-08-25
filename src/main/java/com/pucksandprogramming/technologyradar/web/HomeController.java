package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                modelAndView.addObject("radarRingSetId", targetRadar.getRadarRingSet().getId());
                modelAndView.addObject("radarCategorySetId", targetRadar.getRadarCategorySet().getId());
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
                modelAndView.addObject("radarRingSetId", radarInstances.get(0).getRadarRingSet().getId());
                modelAndView.addObject("radarCategorySetId", radarInstances.get(0).getRadarCategorySet().getId());
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
                modelAndView.addObject("radarRingSetId", radarInstance.getRadarRingSet().getId());
                modelAndView.addObject("radarCategorySetId", radarInstance.getRadarCategorySet().getId());
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
            modelAndView.addObject("radarRingSetId", radarInstances.get(0).getRadarRingSet().getId());
            modelAndView.addObject("radarCategorySetId", radarInstances.get(0).getRadarCategorySet().getId());
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

    @GetMapping(value = { "/public/home/user/{userId}/RadarType/{radarTypeId}/Version/{radarTypeVersion}/Radar/FullView"})
    public ModelAndView mostRecentRadarByType(  @PathVariable Long userId,
                                                @PathVariable String radarTypeId,
                                                @PathVariable Long radarTypeVersion)

    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTypeId", radarTypeId);
        modelAndView.addObject( "radarTypeVersion", radarTypeVersion);

        RadarUser dataOwner = this.radarUserService.findOne(userId);

        if(dataOwner!=null && dataOwner.getUserType().getId()!= UserType.Free)
        {
            modelAndView.addObject("radarInstanceId", -1);
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
