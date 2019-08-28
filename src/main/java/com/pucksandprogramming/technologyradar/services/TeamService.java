package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.FullRadarRepository;
import com.pucksandprogramming.technologyradar.data.repositories.RadarRepositoryBase;
import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.data.repositories.TeamRepository;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
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
    FullRadarRepository fullRadarRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, RadarUserRepository radarUserRepository, FullRadarRepository fullRadarRepository)
    {
        super(radarUserRepository);

        this.teamRepository = teamRepository;
        this.fullRadarRepository = fullRadarRepository;
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
            retVal = (List<Team>)this.teamRepository.findAll();
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

    public Team updateRadarAccess(Long userId, Long teamId, Long radarId, boolean allowAccess)
    {
        Team retVal = this.teamRepository.findOne(teamId);
        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(retVal.getOwner().getId() == userId && this.canModifyTeams(dataOwner))
        {
            Radar targetRadar = this.fullRadarRepository.findOne(radarId);

            if(targetRadar!= null && targetRadar.getRadarUser().getId()==userId)
            {
                if (allowAccess)
                {
                    retVal.addRadar(targetRadar);
                }
                else
                {
                    retVal.removeRadar(targetRadar);
                }

                retVal = this.teamRepository.save(retVal);
            }
        }

        return retVal;
    }

    public Team updateMemberAccess(Long userId, Long teamId, Long teamMemberId, boolean allowAccess)
    {
        Team retVal = this.teamRepository.findOne(teamId);
        RadarUser dataOwner = this.getRadarUserRepository().findOne(userId);

        if(retVal.getOwner().getId() == userId && this.canModifyTeams(dataOwner))
        {
            RadarUser targetMember = this.getRadarUserRepository().findOne(teamMemberId);

            if(targetMember!= null)
            {
                if (allowAccess)
                {
                    retVal.addMember(targetMember);
                }
                else
                {
                    retVal.removeMember(targetMember);
                }

                retVal = this.teamRepository.save(retVal);
            }
        }

        return retVal;
    }
}
