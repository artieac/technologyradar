package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarUserEntity;
import com.pucksandprogramming.technologyradar.data.Entities.TeamEntity;
import com.pucksandprogramming.technologyradar.data.Entities.UserTypeEntity;
import com.pucksandprogramming.technologyradar.data.dao.RadarDAO;
import com.pucksandprogramming.technologyradar.data.dao.RadarUserDAO;
import com.pucksandprogramming.technologyradar.data.dao.TeamDAO;
import com.pucksandprogramming.technologyradar.data.dao.UserTypeDAO;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepository extends SimpleDomainRepository<Team, TeamEntity, TeamDAO, Long>
{
    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    EntityManager entityManager;

    @Autowired
    RadarDAO radarDAO;

    @Autowired
    public void TeamRepository(TeamDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public TeamRepository()
    {
        super(Team.class);
    }

    private List<Team> mapList(List<TeamEntity> source)
    {
        List<Team> retVal = new ArrayList<>();

        if (source != null)
        {
            for (int i = 0; i < source.size(); i++)
            {
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

    public List<Team> findTeamsUserBelongsTo(Long memberId)
    {
        List<Team> retVal = new ArrayList<>();

        Query query = entityManager.createNamedQuery("findByMemberId");
        query.setParameter("memberId", memberId);
        List<TeamEntity> foundItems = query.getResultList();

        if (foundItems != null && foundItems.isEmpty()==false)
        {
            retVal = this.mapList(foundItems);
        }

        return retVal;
    }

    public List<Team> findByRadarAndMember(Long radarId, Long memberId)
    {
        List<Team> retVal = new ArrayList<>();

        Query query = entityManager.createNamedQuery("findByRadarIdAndMemberId");
        query.setParameter("memberId", memberId);
        query.setParameter("radarId", radarId);
        List<TeamEntity> foundItems = query.getResultList();

        if (foundItems != null && foundItems.isEmpty()==false)
        {
            retVal = this.mapList(foundItems);
        }

        return retVal;
    }

    @Override
    public Team save(Team itemToSave)
    {
        TeamEntity targetEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            if (itemToSave != null && itemToSave.getId() != null && itemToSave.getId() > 0)
            {
                targetEntity = this.entityRepository.findOne(itemToSave.getId());
            }
            else
            {
                targetEntity = new TeamEntity();
            }

            // THe mapper doesn't overwrite an instance so I keep getting transient errors
            // for now manually map it, and later look for another mapper
            ///.... this sucks
            if (targetEntity != null)
            {
                targetEntity.setId(itemToSave.getId());
                targetEntity.setName(itemToSave.getName());
                targetEntity.setOwner(radarUserDAO.findOne(itemToSave.getOwner().getId()));

                /// Process the users
                // first add in missing ones
                for(RadarUser sourceTeamMember : itemToSave.getMembers())
                {
                    boolean foundMatch = false;

                    for(RadarUserEntity entityTeamMember : targetEntity.getMembers())
                    {
                        if(entityTeamMember.getId().compareTo(sourceTeamMember.getId())==0)
                        {
                            foundMatch = true;
                            break;
                        }
                    }

                    if(foundMatch==false)
                    {
                        targetEntity.getMembers().add(radarUserDAO.findOne(sourceTeamMember.getId()));
                    }
                }

                // next remove extra ones
                for(RadarUserEntity entityTeamMember : targetEntity.getMembers())
                {
                    boolean foundMatch = false;

                    for(RadarUser sourceTeamMember : itemToSave.getMembers())
                    {
                        if(sourceTeamMember.getId().compareTo(entityTeamMember.getId())==0)
                        {
                            foundMatch = true;
                            break;
                        }
                    }

                    if(foundMatch==false)
                    {
                        targetEntity.getMembers().remove(entityTeamMember);
                    }
                }


                /// Process the radars
                // first add in missing ones
                for(Radar sourceRadar : itemToSave.getRadars())
                {
                    boolean foundMatch = false;

                    for(RadarEntity entityRadar : targetEntity.getRadars())
                    {
                        if(entityRadar.getId().compareTo(sourceRadar.getId())==0)
                        {
                            foundMatch = true;
                            break;
                        }
                    }

                    if(foundMatch==false)
                    {
                        targetEntity.getRadars().add(radarDAO.findOne(sourceRadar.getId()));
                    }
                }

                // next remove extra ones
                if(targetEntity.getRadars()!=null && targetEntity.getRadars().size() > 0)
                {
                    for (int i = targetEntity.getRadars().size() - 1; i > -1; i--)
                    {
                        RadarEntity entityRadar = targetEntity.getRadars().get(i);

                        boolean foundMatch = false;

                        for (Radar sourceRadar : itemToSave.getRadars())
                        {
                            if (sourceRadar.getId().compareTo(entityRadar.getId()) == 0)
                            {
                                foundMatch = true;
                                break;
                            }
                        }

                        if (foundMatch == false)
                        {
                            targetEntity.getRadars().remove(i);
                        }
                    }
                }
            }

            if (targetEntity != null)
            {
                targetEntity = this.entityRepository.save(targetEntity);
            }
        }

        return this.modelMapper.map(this.findOne(targetEntity.getId()), Team.class);
    }
}