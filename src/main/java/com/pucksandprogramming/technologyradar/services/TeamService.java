package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.data.repositories.TeamRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
public class TeamService extends ServiceBase {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, RadarUserRepository radarUserRepository) {
        super(radarUserRepository);

        this.teamRepository = teamRepository;
    }

    private boolean canModifyTeams(RadarUser dataOwner){
        return this.canModifyTeams(Optional.ofNullable(dataOwner));
    }

    private boolean canModifyTeams(Optional<RadarUser> dataOwner) {
        boolean retVal = false;

        if(dataOwner.isPresent()) {
            if (this.getAuthenticatedUser().getUserId() == dataOwner.get().getId()) {
                retVal = true;
            }
        }

        return retVal;
    }

    public List<Team> findAll(Long userId) {
        List<Team> retVal = new ArrayList<>();

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);

        if(dataOwner.isPresent()) {
            retVal = this.teamRepository.findAllList();
        }

        return retVal;
    }

    public Team addTeam(Long userId, String teamName) {
        Team retVal = null;

        Optional<RadarUser> dataOwner = this.getRadarUserRepository().findById(userId);

        if(this.canModifyTeams(dataOwner)) {
            Team newTeam = new Team();
            newTeam.setId(-1L);
            newTeam.setName(teamName);
            newTeam.setOwner(dataOwner.get());

            retVal = this.teamRepository.save(newTeam);
        }

        return retVal;
    }

    public Optional<Team> findByUserAndTeam(Long userId, Long teamId) {
       if (this.getAuthenticatedUser().getUserId() == userId || this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
            return this.teamRepository.findById(teamId);
        }

        return Optional.empty();
    }

    public Optional<Team> addMember(Long teamId, Long newTeamMemberId) {
        Optional<Team> retVal = this.teamRepository.findById(teamId);
        Optional<RadarUser> newTeamMember = this.getRadarUserRepository().findById(newTeamMemberId);

        if(retVal.isPresent() && newTeamMember.isPresent()) {
            if(this.canModifyTeams(retVal.get().getOwner())) {
                retVal.get().addTeamMember(newTeamMember.get());
                retVal = Optional.ofNullable(this.teamRepository.save(retVal.get()));
            }
        }

        return retVal;
    }
}
