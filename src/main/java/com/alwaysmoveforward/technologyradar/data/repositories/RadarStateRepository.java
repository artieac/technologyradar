package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dao.RadarStateDAO;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarState;
import com.alwaysmoveforward.technologyradar.data.dto.RadarStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/19/2016.
 */
@Repository
public class RadarStateRepository extends SimpleDomainRepository<RadarState, RadarStateDTO, RadarStateDAO, Long>
{
    @Autowired
    public void setEntityRepository(RadarStateDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarStateRepository()
    {
        super(RadarState.class);
    }

    public List<RadarState> GetAllOrdered()
    {
        List<RadarState> retVal = new ArrayList<RadarState>();

        Iterable<RadarStateDTO> radarStates = this.entityRepository.findAll(new Sort(Sort.Direction.DESC, "DisplayOrder"));

        for (RadarStateDTO radarState : radarStates)
        {
            retVal.add(this.modelMapper.map(radarState, RadarState.class));
        }

        return retVal;
    }
}

