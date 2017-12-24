package com.alwaysmoveforward.technologyradar.data.repositories;

import com.alwaysmoveforward.technologyradar.data.Entities.TechnologyEntity;
import com.alwaysmoveforward.technologyradar.data.dao.TechnologyDAO;
import com.alwaysmoveforward.technologyradar.domainmodel.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acorrea on 10/18/2016.
 */
@Repository
public class TechnologyRepository extends SimpleDomainRepository<Technology, TechnologyEntity, TechnologyDAO, Long>
{
    @Autowired
    EntityManager entityManager;

    @Autowired
    public void setEntityRepository(TechnologyDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public TechnologyRepository() {
        super(Technology.class);
    }

    private List<Technology> mapList(List<TechnologyEntity> source)
    {
        List<Technology> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(int i = 0; i < source.size(); i++)
            {
                Technology newItem = this.modelMapper.map(source.get(i), Technology.class);
                retVal.add(newItem);
            }
        }

        return retVal;

    }
    public Technology findByName(String name)
    {
        Technology retVal = null;

        TechnologyEntity foundItem = this.entityRepository.findByName(name);

        if(foundItem!=null)
        {
            retVal = this.modelMapper.map(foundItem, Technology.class);
        }

        return retVal;
    }

    // Simple name search for now
    public List<Technology> searchByName(String technologyName)
    {
        List<TechnologyEntity> foundItems = this.entityRepository.findByNameStartingWith(technologyName);
        return this.mapList(foundItems);
    }

    public List<Technology> findByRadarCategoryId(Long radarCategoryId)
    {
        List<TechnologyEntity> foundItems = this.entityRepository.findByRadarCategoryId(radarCategoryId);
        return this.mapList(foundItems);
    }


    public List<Technology> findByRadarRingId(Long radarRingId)
    {
        Query query = entityManager.createNamedQuery("findByRadarRingId");
        query.setParameter("radarRingId", radarRingId);
        List<TechnologyEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<Technology> findByNameAndRadarRingId(String technologyName, Long radarRingId)
    {
        Query query = entityManager.createNamedQuery("findByNameAndRadarRingId");
        query.setParameter("technologyName", "%" + technologyName + "%");
        query.setParameter("radarRingId", radarRingId);
        List<TechnologyEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<Technology> findByNameAndRadarCategoryId(String technologyName, Long radarCategoryId)
    {
        Query query = entityManager.createNamedQuery("findByNameAndRadarCategoryId");
        query.setParameter("technologyName", "%" + technologyName + "%");
        query.setParameter("radarCategoryId", radarCategoryId);
        List<TechnologyEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<Technology> findByRadarRingIdAndRadarCategoryId(Long radarRingId, Long radarCategoryId)
    {
        Query query = entityManager.createNamedQuery("findByRadarRingIdAndRadarCategoryId");
        query.setParameter("radarRingId", radarRingId);
        query.setParameter("radarCategoryId", radarCategoryId);
        List<TechnologyEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }

    public List<Technology> findByNameRadarRingIdAndRadarCategoryId(String technologyName, Long radarRingId, Long radarCategoryId)
    {
        Query query = entityManager.createNamedQuery("findByNameRadarRingIdAndRadarCategoryId");
        query.setParameter("technologyName", "%" + technologyName + "%");
        query.setParameter("radarRingId", radarRingId);
        query.setParameter("radarCategoryId", radarCategoryId);
        List<TechnologyEntity> foundItems = query.getResultList();
        return this.mapList(foundItems);
    }
}
