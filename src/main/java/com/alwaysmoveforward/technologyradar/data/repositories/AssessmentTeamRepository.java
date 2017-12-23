package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.Entities.AssessmentTeamEntity;
import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import com.alwaysmoveforward.technologyradar.data.dao.AssessmentTeamDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
@Repository
public class AssessmentTeamRepository extends SimpleDomainRepository<AssessmentTeam, AssessmentTeamEntity, AssessmentTeamDAO, Long>
{
    @Autowired
    public void setEntityRepository(AssessmentTeamDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public AssessmentTeamRepository()
    {
        super(AssessmentTeam.class);
    }

    public List<AssessmentTeam> findAll()
    {
        List<AssessmentTeam> retVal = new ArrayList<AssessmentTeam>();

        Iterable<AssessmentTeamEntity> foundItems = this.entityRepository.findAll();

        for (AssessmentTeamEntity foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, AssessmentTeam.class));
        }

        return retVal;
    }

    public AssessmentTeam findByName(String name)
    {
        AssessmentTeam retVal = null;

        AssessmentTeamEntity foundItem = this.entityRepository.findByName(name);

        if(foundItem!=null)
        {
            retVal = this.modelMapper.map(foundItem, AssessmentTeam.class);
        }

        return retVal;
    }

    @Override
    public AssessmentTeam save(AssessmentTeam assessmentTeam)
    {
        AssessmentTeamEntity itemToSave = this.modelMapper.map(assessmentTeam, AssessmentTeamEntity.class);

        if(itemToSave != null)
        {
            this.entityRepository.save(itemToSave);
        }

        return null;
    }
}
