package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.TeamEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.data.dao.TeamDAO;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepository extends SimpleDomainRepository<Team, TeamEntity, TeamDAO, Long> {
    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public void TeamRepository(TeamDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public TeamRepository()
    {
        super(Team.class);
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
    protected TeamEntity findOne(Team domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    @Override
    public Team save(Team itemToSave) {
        TeamEntity targetEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null) {
            if (itemToSave != null && itemToSave.getId() != null && itemToSave.getId() > 0) {
                targetEntity = this.entityRepository.findOne(itemToSave.getId());
            }
            else {
                targetEntity = new TeamEntity();
            }

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (targetEntity != null) {
                targetEntity.setId(itemToSave.getId());
                targetEntity.setName(itemToSave.getName());
                targetEntity.setOwner(radarUserDAO.findOne(itemToSave.getOwner().getId()));
            }

            if (targetEntity != null) {
                targetEntity = this.entityRepository.save(targetEntity);
            }
        }

        return this.modelMapper.map(this.findOne(targetEntity.getId()), Team.class);
    }
}