package com.alwaysmoveforward.technologyradar.web.API;

import com.alwaysmoveforward.technologyradar.web.Models.TechnologyBreakdown;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.domainmodel.TechnologyAssessment;
import com.alwaysmoveforward.technologyradar.services.TechnologyAssessmentService;
import com.alwaysmoveforward.technologyradar.web.HomeController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/api/technology")
public class TechnologyController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private TechnologyAssessmentService technologyAssessmentService;

    @RequestMapping("/{id}/assessments")
    public @ResponseBody
    TechnologyBreakdown getTechnologyAssessments(@PathVariable Long id)
    {
        Technology targetTechnology = this.technologyAssessmentService.findTechnologyById(id);
        TechnologyBreakdown retVal = new TechnologyBreakdown(targetTechnology);

        if(targetTechnology != null)
        {
            // TBD this gets all the assessment items at the moment, ideally it would just pull back the ones targeted
            // that would make the subsequent calls at lot easier to manage.
            List<TechnologyAssessment> foundItems = technologyAssessmentService.getTechnologyAssessmentsByTechnologyId(id);

            for(int i = 0; i < foundItems.size(); i++)
            {
                retVal.addTechnologyAssessment(foundItems.get(i));
            }
        }

        return retVal;
    }
}
