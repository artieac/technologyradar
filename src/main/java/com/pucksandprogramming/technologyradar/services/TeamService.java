package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.data.repositories.TeamRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamService extends ServiceBase
{
    TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, RadarUserRepository radarUserRepository)
    {
        super(radarUserRepository);

        this.teamRepository = teamRepository;
    }

    private boolean canModifyTeams(RadarUser dataOwner)
    {
        boolean retVal = false;

        if(dataOwner!=null)
        {
            if (this.getAuthenticatedUser().getUserId() == dataOwner.getId())
            {
                retVal = true;
            }
        }

        return retVal;
    }

    public List<Team> findAll(Long userId)
    {
        List<Team> retVal = new ArrayList<>();

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(dataOwner!=null)
        {
            retVal = this.teamRepository.findAllList();
        }

        return retVal;
    }

    public Team addTeam(Long userId, String teamName)
    {
        Team retVal = null;

        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(this.canModifyTeams(dataOwner))
        {
            Team newTeam = new Team();
            newTeam.setId(-1L);
            newTeam.setName(teamName);
            newTeam.setOwner(dataOwner);

            retVal = this.teamRepository.save(newTeam);
        }

        return retVal;
    }

    public Team findByUserAndTeam(Long userId, Long teamId)
    {
        Team retVal = null;

        if (this.getAuthenticatedUser().getUserId() == userId || this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))
        {
            retVal = this.teamRepository.findOne(teamId);
        }

        return retVal;
    }

    public Team addMember(Long teamId, Long newTeamMemberId)
    {
        Team retVal = this.teamRepository.findOne(teamId);
        RadarUser newTeamMember = this.getRadarUserRepository().findOne(newTeamMemberId);

        if(retVal!=null && newTeamMember != null)
        {
            if(this.canModifyTeams(retVal.getOwner()))
            {
                retVal.addTeamMember(newTeamMember);
                retVal = this.teamRepository.save(retVal);
            }
        }

        return retVal;
    }
}
