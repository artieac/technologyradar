package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.services.AssessmentTeamService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by acorrea on 10/27/2016.
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private AssessmentTeamService assessmentTeamService;

    @RequestMapping("/index")
    public String index(Model viewModel)
    {
        return "adminIndex";
    }

    @RequestMapping("/manage/teams")
    public String manageTeams(Model model)
    {
        model.addAttribute("assessmentTeams", this.assessmentTeamService.getAssessmentTeams());
        return "/admin/manageteams";
    }
}
