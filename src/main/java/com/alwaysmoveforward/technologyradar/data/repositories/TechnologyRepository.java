package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.dto.TechnologyDTO;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import com.alwaysmoveforward.technologyradar.data.dao.TechnologyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by acorrea on 10/18/2016.
 */
@Repository
public class TechnologyRepository extends SimpleDomainRepository<Technology, TechnologyDTO, TechnologyDAO, Long>
{
    @Autowired
    public void setEntityRepository(TechnologyDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public TechnologyRepository() {
        super(Technology.class);
    }

    public Technology findByName(String name)
    {
        Technology retVal = null;

        TechnologyDTO foundItem = this.entityRepository.findByName(name);

        if(foundItem!=null)
        {
            retVal = this.modelMapper.map(foundItem, Technology.class);
        }

        return retVal;
    }
}
