package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.AssessmentTeamDAO;
import com.alwaysmoveforward.technologyradar.data.dto.AssessmentTeamDTO;
import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/21/2016.
 */
@Repository
public class AssessmentTeamRepository extends SimpleDomainRepository<AssessmentTeam, AssessmentTeamDTO, AssessmentTeamDAO, Long>
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

        Iterable<AssessmentTeamDTO> foundItems = this.entityRepository.findAll();

        for (AssessmentTeamDTO foundItem : foundItems)
        {
            retVal.add(this.modelMapper.map(foundItem, AssessmentTeam.class));
        }

        return retVal;
    }

    public AssessmentTeam findByName(String name)
    {
        AssessmentTeam retVal = null;

        AssessmentTeamDTO foundItem = this.entityRepository.findByName(name);

        if(foundItem!=null)
        {
            retVal = this.modelMapper.map(foundItem, AssessmentTeam.class);
        }

        return retVal;
    }

    @Override
    public AssessmentTeam save(AssessmentTeam assessmentTeam)
    {
        AssessmentTeamDTO itemToSave = this.modelMapper.map(assessmentTeam, AssessmentTeamDTO.class);

        if(itemToSave != null)
        {
            this.entityRepository.save(itemToSave);
        }

        return null;
    }
}
