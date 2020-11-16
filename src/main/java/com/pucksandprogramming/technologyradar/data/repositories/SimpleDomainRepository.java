package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class SimpleDomainRepository<
        DomainModel,
        Entity,
        EntityRepository extends PagingAndSortingRepository<Entity, ID>,
        ID extends Serializable>
        implements PagingAndSortingRepository<DomainModel, ID> {
    private static final Logger logger = Logger.getLogger(SimpleDomainRepository.class);

    @Autowired
    protected RadarMapper modelMapper;

    protected Class<DomainModel> domainModelClass;

    protected EntityRepository entityRepository;

    protected void setEntityRepository(EntityRepository entityRepository)
    {
        this.entityRepository = entityRepository;
    }

    public SimpleDomainRepository() {
    }

    protected SimpleDomainRepository(Class<DomainModel> domainModelClass)
    {
        this.domainModelClass = domainModelClass;
    }

    protected abstract Entity findOne(DomainModel domainModel);

    @Override
    public Iterable<DomainModel> findAll() {
        List<DomainModel> retVal = new ArrayList<>();

        Iterable<Entity> foundItems = this.entityRepository.findAll();

        if (foundItems != null) {
            for (Entity entity : foundItems) {
                retVal.add(modelMapper.map(entity, domainModelClass));
            }
        }

        return retVal;
    }

    @Override
    public Iterable<DomainModel> findAll(Iterable<ID> iterable)
    {
        return null;
    }

    @Override
    public Iterable<DomainModel> findAll(Sort sort) {
        Iterable<Entity> sortedEntities = entityRepository.findAll(sort);
        List<DomainModel> domainModels = new ArrayList<>();

        if (sortedEntities != null) {
            for (Entity entity : sortedEntities) {
                domainModels.add(modelMapper.map(entity, domainModelClass));
            }
        }

        return domainModels;
    }

    @Override
    public Page<DomainModel> findAll(Pageable pageable) {
        Page<Entity> entitiesPage = entityRepository.findAll(pageable);

        if (entitiesPage != null && entitiesPage.hasContent()) {
            List<Entity> entities = entitiesPage.getContent();
            List<DomainModel> domainModels = new ArrayList<>(entities.size());

            for (Entity entity : entities) {
                domainModels.add(modelMapper.map(entity, domainModelClass));
            }

            return new PageImpl<>(domainModels, pageable, entities.size());
        }
        return null;
    }

    public List<DomainModel> findAllList() {
        List<DomainModel> domainModels = new ArrayList<>();

        Iterable<Entity> entities = this.entityRepository.findAll();

        if(entities != null) {
            for(Entity entity : entities) {
                domainModels.add(this.modelMapper.map(entity, domainModelClass));
            }
        }

        return domainModels;
    }
    /**
     * Calls {@link #findAll(Pageable)} to get {@link Page} and fetches content and returns as {@link List}
     *
     * @param pageable
     * @return
     */
    public List<DomainModel> findAllList(Pageable pageable) {
        List<DomainModel> domainModels = null;
        Page<DomainModel> entitiesPage = findAll(pageable);
        if (entitiesPage != null && entitiesPage.hasContent()) {
            domainModels = new ArrayList<>(entitiesPage.getSize());
            domainModels = entitiesPage.getContent();
        }
        return domainModels;
    }

    @Override
    public <S extends DomainModel> S save(S s) {
//        Long targetId = new Long();
//        Method method = DomainModel.class.getDeclaredMethod ("getId");

//        try{
//            targetId = (Long)method.invoke(s);
//        }
//        catch(IllegalAccessException iae){
//            logger.error(iae);
//        }
//        catch(InvocationTargetException ite) {
//            logger.error(ite);
//        }

//        Entity entity = entityRepository.findOne(targetId);

//        if(entity!=null){
//            entity = this.modelMapper.map(s);
//        }
        return null;
    }

    @Override
    public <S extends DomainModel> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public DomainModel findOne(ID id) {
        Entity entity = entityRepository.findOne(id);
        if (entity != null) {
            return modelMapper.map(entity, domainModelClass);
        }
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return entityRepository.exists(id);
    }

    @Override
    public long count() {
        return entityRepository.count();
    }

    @Override
    public void delete(ID id) {
        this.entityRepository.delete(id);
    }

    @Override
    public void delete(DomainModel domainModel) {
        Entity targetItem = this.findOne(domainModel);

        if(targetItem!=null) {
            this.entityRepository.delete(targetItem);
        }
    }

    @Override
    public void delete(Iterable<? extends DomainModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
