package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.*;
import com.pucksandprogramming.technologyradar.services.RadarInstance.RadarService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by acorrea on 10/14/2016.
 */

@Controller
@RequestScope
@ControllerAdvice
public class HomeController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(HomeController.class);

    private final RadarService radarService;
    private final RadarUserService radarUserService;

    @Autowired
    public HomeController(RadarService radarService,
                          RadarUserService radarUserService){
        this.radarService = radarService;
        this.radarUserService = radarUserService;
    }

    @RequestMapping( value = {"/", "/public/home/index"})
    public String index(Model viewModel)
    {
        return "home/index";
    }

    @RequestMapping(value = { "/home/secureradar", "/home/secureradar/{radarInstanceId}" })
    public ModelAndView secureRadar(@PathVariable Optional<Long> radarInstanceId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<RadarUser> currentUser = this.getCurrentUser();

        if(currentUser.isPresent()) {
            modelAndView.addObject("userId", currentUser.get().getId());
        }

        if(radarInstanceId.isPresent()) {
            Optional<Radar> targetRadar = this.radarService.findById(radarInstanceId.get());

            if (targetRadar.isPresent()) {
                modelAndView.addObject("radarInstanceId", radarInstanceId.get());
                modelAndView.addObject("radarTemplateId", targetRadar.get().getRadarTemplate().getId());
            }
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    @RequestMapping(value = { "/home/user/{userId}/radars"})
    public ModelAndView viewUserRadars(@PathVariable Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        Optional<RadarUser> targetDataOwner = this.radarUserService.findOne(userId);

        if(targetDataOwner.isPresent()) {
            List<Radar> radarInstances = this.radarService.findByRadarUserId(userId);

            if (radarInstances != null && radarInstances.size() > 0) {
                modelAndView.addObject("radarTemplateId", radarInstances.get(0).getRadarTemplate().getId());

                if (targetDataOwner.get().getUserType().getGrantedRights().get(UserRights.CanSeeFullView) == 1) {
                    modelAndView.addObject("radarInstanceId", -1);
                } else {
                    modelAndView.addObject("radarInstanceId", radarInstances.get(0).getId());
                }
            }
        }

        modelAndView.setViewName("home/radar");

        return modelAndView;
    }

    // I hate this url format, but I can't figure out how to get seccurity working with the
    // format that I want
    @RequestMapping(value = { "/public/home/user/{userId}/radar/{radarInstanceId}" })
    public ModelAndView publicRadar(@PathVariable Long userId, @PathVariable Optional<Long> radarInstanceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        if(radarInstanceId.isPresent()) {
            modelAndView.addObject("radarInstanceId", radarInstanceId.get());

            // TBD if they can't share with others make sure this is the most recent.
            Radar radarInstance = this.radarService.findByUserAndRadarId(userId, radarInstanceId.get());

            if(radarInstance!=null) {
                modelAndView.addObject("radarTemplateId", radarInstance.getRadarTemplate().getId());
            }
        }

        modelAndView.setViewName("home/radar");
        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/user/{userId}/radars/mostrecent",  "/public/home/user/{userId}/radars"})
    public ModelAndView mostRecentRadar(@PathVariable Long userId,
                                        @RequestParam(name="embeddable", required = false, defaultValue="false") Boolean isEmbeddable) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);

        Optional<RadarUser> targetDataOwner = this.radarUserService.findOne(userId);

        if(targetDataOwner.isPresent()) {
            List<Radar> radarInstances = this.radarService.findByRadarUserId(userId);

            if (radarInstances != null && radarInstances.size() > 0) {
                modelAndView.addObject("radarTemplateId", radarInstances.get(0).getRadarTemplate().getId());

                if (targetDataOwner.get().getUserType().getGrantedRights().get(UserRights.CanSeeFullView) == 1) {
                    modelAndView.addObject("radarInstanceId", -1);
                } else {
                    modelAndView.addObject("radarInstanceId", radarInstances.get(0).getId());
                }
            }
        }
        
        if(isEmbeddable) {
            modelAndView.setViewName("home/embeddedradar");
        }
        else {
            modelAndView.setViewName("home/radar");
        }

        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/user/{userId}/radartemplate/{radarTemplateId}/radars"})
    public ModelAndView mostRecentRadarByType(  @PathVariable Long userId,
                                                @PathVariable Long radarTemplateId,
                                                @RequestParam(name="mostrecent", required = false, defaultValue="false") boolean mostRecent)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTemplateId", radarTemplateId);

        if(mostRecent==true) {
            List<Radar> radarInstances = this.radarService.findByUserAndType(userId, radarTemplateId);

            if (radarInstances != null && radarInstances.size() > 0) {
                Radar mostRecentRadar = Collections.max(radarInstances, new MostRecentRadarComparator());

                if (mostRecentRadar != null) {
                    modelAndView.addObject("radarInstanceId", mostRecentRadar.getId());
                }
            }
        }

        modelAndView.setViewName("home/radar");

        return modelAndView;
    }

    @GetMapping(value = { "/public/home/user/{userId}/radartemplate/{radarTemplateId}/radar/fullview"})
    public ModelAndView mostRecentRadarByType(  @PathVariable Long userId,
                                                @PathVariable Long radarTemplateId)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("radarTemplateId", radarTemplateId);

        Optional<RadarUser> dataOwner = this.radarUserService.findOne(userId);

        if(dataOwner!=null && dataOwner.get().getUserType().getId()!= UserType.Free) {
            modelAndView.addObject("radarInstanceId", -1);
        }

        modelAndView.setViewName("home/radar");

        return modelAndView;
    }

    @RequestMapping(value = { "/public/home/about", "/public/home/about" })
    public ModelAndView About() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/about");
        return modelAndView;
    }
}
