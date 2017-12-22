package com.alwaysmoveforward.technologyradar.services;

import com.alwaysmoveforward.technologyradar.data.repositories.AssessmentTeamRepository;
import com.alwaysmoveforward.technologyradar.domainmodel.AssessmentTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by acorrea on 10/27/2016.
 */
@Component
public class AssessmentTeamService
{
    @Autowired
    private AssessmentTeamRepository assessmentTeamRepository;

    public List<AssessmentTeam> getAssessmentTeams()
    {
        return this.assessmentTeamRepository.findAll();
    }

    public AssessmentTeam findOne(Long teamId)
    {
        return this.assessmentTeamRepository.findOne(teamId);
    }

    public AssessmentTeam addTeam(String name)
    {
        AssessmentTeam retVal = null;

        if(!name.isEmpty())
        {
            AssessmentTeam existingTeam = this.assessmentTeamRepository.findByName(name);

            if(existingTeam == null)
            {
                retVal = new AssessmentTeam();
                retVal.setName(name);
                retVal.setCreateDate(new Date());
                this.assessmentTeamRepository.save(retVal);
            }
        }

        return retVal;
    }

    public AssessmentTeam updateTeam(Long teamId, String name)
    {
        AssessmentTeam retVal = null;

        if(!name.isEmpty())
        {
            retVal = this.assessmentTeamRepository.findOne(teamId);

            if(retVal != null)
            {
                retVal.setName(name);
                this.assessmentTeamRepository.save(retVal);
            }
        }

        return retVal;
    }
}
