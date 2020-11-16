package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.services.*;
import com.pucksandprogramming.technologyradar.services.RadarTemplate.AssociatedRadarTemplateService;
import com.pucksandprogramming.technologyradar.services.RadarTemplate.RadarTemplateService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarTemplateViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class RadarTemplateController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(RadarController.class);

    @Autowired
    private RadarUserService radarUserService;

    @Autowired
    RadarTemplateService radarTemplateService;

    @Autowired
    AssociatedRadarTemplateService associatedRadarTemplateService;

    @GetMapping(value = "/public/RadarTemplates", produces = "application/json")
    public @ResponseBody List<RadarTemplate> getPublicRadarTemplates() {
        List<RadarTemplate> retVal = new ArrayList();

        if (this.getCurrentUser() != null) {
            retVal = radarTemplateService.findByUserId(this.getCurrentUserId());
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{radarUserId}/RadarTemplates", "/public/User/{radarUserId}/RadarTemplates"}, produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getRadarTemplatesByUserId(@PathVariable Long radarUserId,
                                                                        @RequestParam(name="includeOwned", required = false, defaultValue = "true") boolean includeOwned,
                                                                        @RequestParam(name="includeAssociated", required = false, defaultValue = "false") boolean includeAssociated) {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            RadarUser targetUser = radarUserService.findOne(radarUserId);
            List<RadarTemplate> foundItems = new ArrayList<RadarTemplate>();

            if (targetUser != null) {
                foundItems = radarTemplateService.findByUserId(radarUserId);

                if (includeAssociated == true && this.getCurrentUser()!=null && this.getCurrentUser().getId()==targetUser.getId()) {
                    List<RadarTemplate> associatedRadarTemplates = this.associatedRadarTemplateService.findAssociatedRadarTemplates(targetUser);
                    foundItems.addAll(associatedRadarTemplates);
                }
            }

            for (RadarTemplate radarTemplateItem : foundItems) {
                retVal.add(new RadarTemplateViewModel(radarTemplateItem));
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = {"/RadarTemplates/Published", "/public/RadarTemplates/Published"}, produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getRadarTemplatesForPublishedRadars() {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            List<RadarTemplate> foundItems = radarTemplateService.findByPublishedRadars(this.getCurrentUserId());

            if(foundItems!=null) {
                for(RadarTemplate radarTemplate : foundItems) {
                    retVal.add(new RadarTemplateViewModel(radarTemplate));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = {"/User/{userId}/RadarTemplates/Radared", "/public/User/{userId}/RadarTemplates/Radared"}, produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getRadarTemplatesForPublishedRadars(@PathVariable Long userId) {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            List<RadarTemplate> foundItems = radarTemplateService.findOwnedWithRadars(userId);

            if(foundItems!=null) {
                for(RadarTemplate radarTemplate : foundItems) {
                    retVal.add(new RadarTemplateViewModel(radarTemplate));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = {"/RadarTemplates/Shared", "/public//RadarTemplates/Shared"}, produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getRadarTemplatesByUserId(@RequestParam(name="excludeUser", required = false, defaultValue = "-1") Long excludeUserId) {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            List<RadarTemplate> foundItems = this.radarTemplateService.findSharedRadarTemplates(excludeUserId);

            for (RadarTemplate radarTemplateItem : foundItems) {
                retVal.add(new RadarTemplateViewModel(radarTemplateItem));
            }

            return retVal;
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/User/{radarUserId}/RadarTemplates/Associated", produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getAssociatedRadarTemplates(@PathVariable Long radarUserId) {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            List<RadarTemplate> foundItems = new ArrayList<RadarTemplate>();

            if (radarUserId != null && radarUserId > 0) {
                RadarUser targetUser = radarUserService.findOne(radarUserId);

                if (targetUser != null) {
                    foundItems = this.associatedRadarTemplateService.findAssociatedRadarTemplates(targetUser);
                }
            }

            for (RadarTemplate radarTemplateItem : foundItems) {
                retVal.add(new RadarTemplateViewModel(radarTemplateItem));
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @GetMapping(value = "/User/{radarUserId}/RadarTemplate/{radarTemplateId}", produces = "application/json")
    public @ResponseBody List<RadarTemplateViewModel> getRadarTemplatesByUserId(@PathVariable Long radarUserId,
                                                                        @PathVariable String radarTemplateId,
                                                                        @RequestParam(name="allVersions", required = false, defaultValue = "false") boolean allVersions) {
        List<RadarTemplateViewModel> retVal = new ArrayList<RadarTemplateViewModel>();

        try {
            RadarUser targetUser = radarUserService.findOne(radarUserId);
            List<RadarTemplate> foundItems = radarTemplateService.findByUserAndRadarTemplate(radarUserId, radarTemplateId);

            for (RadarTemplate radarTemplateItem : foundItems) {
                retVal.add(new RadarTemplateViewModel(radarTemplateItem));
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{radarUserId}/RadarTemplate", produces = "application/json")
    public @ResponseBody
    RadarTemplateViewModel addRadarTemplate(@RequestBody RadarTemplateViewModel radarTemplateViewModel, @PathVariable Long radarUserId) {
        RadarTemplateViewModel retVal = null;

        try {
            if (radarTemplateViewModel != null) {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null) {
                    RadarTemplate radarTemplate = radarTemplateViewModel.ConvertToRadarTemplate();
                    radarTemplate = radarTemplateService.update(radarTemplate, radarUserId);
                    retVal = new RadarTemplateViewModel(radarTemplate);
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{radarUserId}/RadarTemplate/{radarTemplateId}", produces = "application/json")
    public @ResponseBody
    RadarTemplateViewModel updateRadarTemplate(@RequestBody RadarTemplateViewModel radarTemplateViewModel, @PathVariable Long radarUserId, @PathVariable Long radarTemplateId) {
        RadarTemplateViewModel retVal = null;

        try {
            if (radarTemplateViewModel != null) {
                RadarUser targetUser = this.radarUserService.findOne(radarUserId);

                if(targetUser != null) {
                    RadarTemplate radarTemplate = radarTemplateViewModel.ConvertToRadarTemplate();
                    radarTemplate = radarTemplateService.update(radarTemplate, radarUserId);
                    retVal = new RadarTemplateViewModel(radarTemplate);
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @PutMapping(value = "/User/{userId}/RadarTemplate/{radarTemplateId}/Associate")
    public @ResponseBody boolean associateRadarTemplate(@RequestBody Map modelMap,
                                                    @PathVariable Long userId,
                                                    @PathVariable Long radarTemplateId) {
        boolean retVal = false;

        try {
            boolean shouldAssociate = Boolean.parseBoolean(modelMap.get("shouldAssociate").toString());

            if (this.getCurrentUser().getId() == userId) {
                retVal = this.associatedRadarTemplateService.associateRadarTemplate(this.getCurrentUser(), radarTemplateId, shouldAssociate);
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

    @DeleteMapping(value = "/User/{userId}/RadarTemplate/{radarTemplateId}")
    public @ResponseBody boolean associateRadarTemplate(@PathVariable Long userId,
                                                    @PathVariable Long radarTemplateId) {
        boolean retVal = false;

        try {
            if (this.getCurrentUser().getId() == userId) {
                retVal = this.radarTemplateService.deleteRadarTemplate(userId, radarTemplateId);
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

}
