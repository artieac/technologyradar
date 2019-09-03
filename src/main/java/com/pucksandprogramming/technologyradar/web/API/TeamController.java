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

@Controller
@RequestMapping("/api")
public class TeamController extends ControllerBase
{
    private static final Logger logger = Logger.getLogger(TeamController.class);

    @Autowired
    TeamService teamService;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/User/{userId}/Teams", produces = "application/json")
    public @ResponseBody
    List<TeamViewModel> getAllByUser(@PathVariable Long userId)
    {
        List<TeamViewModel> retVal = new ArrayList<>();

        List<Team> foundItems = this.teamService.findAll(userId);

        if(foundItems!=null)
        {
            for(Team foundItem : foundItems)
            {
                retVal.add(new TeamViewModel(foundItem));
            }
        }

        return retVal;
    }

    @GetMapping(value = "/User/{userId}/Team/{teamId}", produces = "application/json")
    public @ResponseBody
    TeamViewModel getAllByUser(@PathVariable Long userId,
                                     @PathVariable Long teamId)
    {
        TeamViewModel retVal = null;

        if(this.getCurrentUserId()==userId || this.getCurrentUser().getRoleId()== Role.RoleType_Admin)
        {
            Team foundItem = this.teamService.findByUserAndTeam(userId, teamId);

            if(foundItem != null)
            {
                retVal = new TeamViewModel(foundItem);
            }
        }

        return retVal;
    }

    @PostMapping(value = "/User/{userId}/Team")
    public @ResponseBody
    List<TeamViewModel> addTeam(@RequestBody Map modelMap, @PathVariable Long userId)
    {
        List<TeamViewModel> retVal = new ArrayList<>();

        try
        {
            String teamName = modelMap.get("name").toString();
            this.teamService.addTeam(userId, teamName);

            List<Team> foundItems = this.teamService.findAll(userId);

            if(foundItems != null)
            {
                for(Team foundItem : foundItems)
                {
                    retVal.add(new TeamViewModel(foundItem));
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        return retVal;
    }

    @PostMapping(value = "/User/{userId}/Team/{teamId}/Radar")
    public @ResponseBody TeamViewModel updateTeamRadar(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody Map modelMap)
    {
        Long radarId = Long.parseLong(modelMap.get("radarId").toString());
        boolean allowAccess = Boolean.parseBoolean(modelMap.get("allowAccess").toString());

        Team retVal = this.teamService.updateRadarAccess(userId, teamId, radarId, allowAccess);

        return new TeamViewModel(retVal);
    }

    @PostMapping(value = "/User/{userId}/Team/{teamId}/Radars")
    public @ResponseBody TeamViewModel updateTeamRadars(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody Map modelMap)
    {
        List<Integer> radarIdParameters = (List<Integer>)modelMap.get("radars");
        List<Long> radarIds = new ArrayList<>();

        if(radarIdParameters!=null)
        {
            for(Integer radarIdParameter : radarIdParameters)
            {
                radarIds.add(new Long(radarIdParameter));
            }
        }

        Team retVal = this.teamService.updateRadarAccess(userId, teamId, radarIds);

        return new TeamViewModel(retVal);
    }

    @PostMapping(value = "/User/{userId}/Team/{teamId}/Members")
    public @ResponseBody TeamViewModel updateTeamMembers(@PathVariable Long userId, @PathVariable Long teamId, @RequestBody Map modelMap)
    {
        List<Integer> memberIdParameters = (List<Integer>)modelMap.get("members");
        List<Long> memberIds = new ArrayList<>();

        if(memberIdParameters!=null)
        {
            for(Integer memberIdParameter : memberIdParameters)
            {
                memberIds.add(new Long(memberIdParameter));
            }
        }

        Team retVal = this.teamService.updateMemberAccess(userId, teamId, memberIds);

        return new TeamViewModel(retVal);
    }
}
