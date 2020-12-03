package com.pucksandprogramming.technologyradar.web.API;

import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import com.pucksandprogramming.technologyradar.services.TeamService;
import com.pucksandprogramming.technologyradar.web.ControllerBase;
import com.pucksandprogramming.technologyradar.web.Models.RadarViewModel;
import com.pucksandprogramming.technologyradar.web.Models.TeamViewModel;
import com.pucksandprogramming.technologyradar.web.Models.UserViewModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class TeamController extends ControllerBase {
    private static final Logger logger = Logger.getLogger(TeamController.class);

    @Autowired
    TeamService teamService;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/User/{userId}/Teams", produces = "application/json")
    public @ResponseBody
    List<TeamViewModel> getAllByUser(@PathVariable Long userId) {
        List<TeamViewModel> retVal = new ArrayList<>();

        List<Team> foundItems = this.teamService.findAll(userId);

        if(foundItems!=null) {
            for(Team foundItem : foundItems) {
                retVal.add(new TeamViewModel(foundItem));
            }
        }

        return retVal;
    }

    @GetMapping(value = "/User/{userId}/Team/{teamId}", produces = "application/json")
    public @ResponseBody
    TeamViewModel getAllByUser(@PathVariable Long userId,
                                     @PathVariable Long teamId) {
        TeamViewModel retVal = null;

        if(this.getCurrentUserId()==userId || this.getCurrentUser().getRoleId()== Role.RoleType_Admin) {
            Optional<Team> foundItem = this.teamService.findByUserAndTeam(userId, teamId);

            if(foundItem.isPresent()) {
                retVal = new TeamViewModel(foundItem.get());
            }
        }

        return retVal;
    }

    @PostMapping(value = "/User/{userId}/Team")
    public @ResponseBody
    List<TeamViewModel> addTeam(@RequestBody Map modelMap, @PathVariable Long userId) {
        List<TeamViewModel> retVal = new ArrayList<>();

        try {
            String teamName = modelMap.get("name").toString();
            this.teamService.addTeam(userId, teamName);

            List<Team> foundItems = this.teamService.findAll(userId);

            if(foundItems != null) {
                for(Team foundItem : foundItems) {
                    retVal.add(new TeamViewModel(foundItem));
                }
            }
        }
        catch(Exception e) {
            logger.error(e);
        }

        return retVal;
    }

}
