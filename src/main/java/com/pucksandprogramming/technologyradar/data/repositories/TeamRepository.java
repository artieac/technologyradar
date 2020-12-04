package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.TeamEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.data.dao.TeamDAO;
import com.pucksandprogramming.technologyradar.data.dao.TechnologyDAO;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository extends SimpleDomainRepository<Team, TeamEntity, TeamDAO, Long> {
    private final RadarUserDAO radarUserDAO;

    @Autowired
    public TeamRepository(RadarMapper modelMapper,
                               TeamDAO entityRepository,
                               RadarUserDAO radarUserDAO) {
        super(modelMapper, entityRepository, Team.class);
        this.radarUserDAO = radarUserDAO;
    }

    private List<Team> mapList(List<TeamEntity> source) {
        List<Team> retVal = new ArrayList<>();

        if (source != null) {
            for (int i = 0; i < source.size(); i++) {
                Team newItem = this.modelMapper.map(source.get(i), Team.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected Optional<TeamEntity> findOne(Team domainModel) {
        return this.entityRepository.findById(domainModel.getId());
    }

    @Override
    public Team save(Team itemToSave) {
        Optional<TeamEntity> teamEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null) {
            if (itemToSave != null && itemToSave.getId() != null && itemToSave.getId() > 0) {
                teamEntity = this.entityRepository.findById(itemToSave.getId());
            }
            else {
                teamEntity = Optional.of(new TeamEntity());
            }

            TeamEntity targetEntity = teamEntity.get();

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (targetEntity != null) {
                targetEntity.setId(itemToSave.getId());
                targetEntity.setName(itemToSave.getName());
                targetEntity.setOwner(radarUserDAO.findById(itemToSave.getOwner().getId()).get());
            }

            if (targetEntity != null) {
                targetEntity = this.entityRepository.save(targetEntity);
            }
        }

        return this.modelMapper.map(this.findOne(itemToSave).get(), Team.class);
    }
}