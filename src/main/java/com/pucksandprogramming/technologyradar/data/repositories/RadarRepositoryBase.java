package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.RadarEntity;
import com.pucksandprogramming.technologyradar.data.Entities.RadarItemEntity;
import com.pucksandprogramming.technologyradar.data.Entities.TechnologyEntity;
import com.pucksandprogramming.technologyradar.data.Entities.VersionedIdEntity;
import com.pucksandprogramming.technologyradar.data.dao.*;
import com.pucksandprogramming.technologyradar.domainmodel.Radar;
import com.pucksandprogramming.technologyradar.domainmodel.RadarItem;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public abstract class RadarRepositoryBase extends SimpleDomainRepository<Radar, RadarEntity, RadarDAO, Long>
{
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    TechnologyDAO technologyDAO;

    @Autowired
    RadarRingDAO radarRingDAO;

    @Autowired
    RadarCategoryDAO radarCategoryDAO;

    @Autowired
    RadarUserDAO radarUserDAO;

    @Autowired
    RadarItemDAO radarItemDAO;

    @Autowired
    RadarTypeDAO radarTypeDAO;

    @Autowired
    public void setEntityRepository(RadarDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public RadarRepositoryBase()
    {
        super(Radar.class);
    }

    protected List<Radar> mapList(List<RadarEntity> source)
    {
        List<Radar> retVal = new ArrayList<>();

        if(source!=null)
        {
            for(RadarEntity radarEntity : source)
            {
                retVal.add(this.modelMapper.map(radarEntity, Radar.class));
            }
        }

        return retVal;
    }

    @Override
    protected RadarEntity findOne(Radar domainModel)
    {
        return this.entityRepository.findOne(domainModel.getId());
    }

    public abstract List<Radar> findByUserId(Long radarUserId);
    public abstract Radar findByUserRadarId(Long radarUserId, Long radarId);
    public abstract List<Radar> findByUserAndType(Long radarUserId, String radarTypeId);
    public abstract List<Radar> findByUserTypeAndVersion(Long radarUserId, String radarTypeId, Long radarVersion);
    public abstract List<Radar> findByRadarSubjectId(Long radarSubjectIdf);
    public abstract List<Radar> findNotOwnedByRadarSubjectAndUser(Long radarUserId, Long radarSubjectId);
    public abstract List<Radar> findOwnedByTechnologyId(Long radarUserId, Long radarSubjectId);
    public abstract List<RadarItem> findCurrentByTypeAndVersion(Long radarUserId, String radarTypeId, Long radarTypeVersion);

    public Radar findByIdAndName(Long radarInstanceId, String assessmentName)
    {
        Radar retVal = null;

        RadarEntity foundItem = this.entityRepository.findByIdAndName(radarInstanceId, assessmentName);

        if (foundItem != null)
        {
            retVal = this.modelMapper.map(foundItem, Radar.class);
        }

        return retVal;
    }

    public List<Radar> findAllPublishedRadarsByUser(Long radarUserId)
    {
        List<Radar> retVal = new ArrayList<>();

        List<RadarEntity> foundItems = this.entityRepository.findAllByRadarUserIdAndIsPublished(radarUserId, true);
        retVal = this.mapList(foundItems);

        return retVal;
    }

    public RadarItem getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId(Long radarUserId, Long previousRadarInstanceId, Long radarSubjectId)
    {
        RadarItem retVal = null;

        Query query = entityManager.createNamedQuery("getRadarItemFromPreviousRadarByRadarUserIdAndSubjectId");
        query.setParameter("technologyId", radarSubjectId);
        query.setParameter("radarUserId", radarUserId);
        query.setParameter("previousRadarInstanceId", previousRadarInstanceId);
        List<RadarItemEntity> foundItems = query.getResultList();

        if (foundItems != null && foundItems.isEmpty()==false)
        {
            retVal = this.modelMapper.map(foundItems.get(0), RadarItem.class);
        }

        return retVal;
    }

    @Override
    public Radar save(Radar itemToSave)
    {
        RadarEntity radarEntity = null;

        if(itemToSave !=null && itemToSave.getId() != null)
        {
            radarEntity = this.entityRepository.findOne(itemToSave.getId());
        }
        else
        {
            radarEntity = new RadarEntity();
        }

        // THe mapper doesn't overwrite an instance so I keep getting transient errors
        // for now manually map it, and later look for another mapper
        ///.... this sucks
        if(radarEntity != null)
        {
            radarEntity.setAssessmentDate(itemToSave.getAssessmentDate());
            radarEntity.setName(itemToSave.getName());
            radarEntity.setRadarUser(radarUserDAO.findOne(itemToSave.getRadarUser().getId()));
            radarEntity.setIsPublished(itemToSave.getIsPublished());
            radarEntity.setIsLocked(itemToSave.getIsLocked());
            VersionedIdEntity versionedIdEntity = new VersionedIdEntity(itemToSave.getRadarType().getId(), itemToSave.getRadarType().getVersion());
            radarEntity.setRadarType(radarTypeDAO.findOne(versionedIdEntity));

            // First remove any deletions
            if(radarEntity != null && radarEntity.getRadarItems() != null)
            {
                for(int i = radarEntity.getRadarItems().size() - 1; i >= 0 ; i--)
                {
                    RadarItemEntity radarInstanceItemEntity = radarEntity.getRadarItems().get(i);

                    boolean foundMatch = false;

                    for (int j = 0; j < itemToSave.getRadarItems().size(); j++) {
                        RadarItem assessmentItem = itemToSave.getRadarItems().get(j);

                        if (assessmentItem.getTechnology().getId() == radarInstanceItemEntity.getTechnology().getId()) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch == false)
                    {
                        radarEntity.getRadarItems().remove(radarInstanceItemEntity);
                        radarItemDAO.delete(radarInstanceItemEntity);
                    }
                }
            }

            // then add in any new ones
            for(int i = 0; i < itemToSave.getRadarItems().size(); i++)
            {
                RadarItem assessmentItem = itemToSave.getRadarItems().get(i);

                boolean foundMatch = false;

                for(int j = 0; j < radarEntity.getRadarItems().size(); j++)
                {
                    RadarItemEntity radarInstanceItemEntity = radarEntity.getRadarItems().get(j);

                    if(radarInstanceItemEntity.getTechnology().getId()==assessmentItem.getTechnology().getId())
                    {
                        foundMatch = true;
                        radarInstanceItemEntity.setDetails(assessmentItem.getDetails());
                        radarInstanceItemEntity.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getRadarCategory().getId()));
                        radarInstanceItemEntity.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                        radarInstanceItemEntity.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                        radarInstanceItemEntity.setState(assessmentItem.getState());
                        break;
                    }
                }

                if(foundMatch == false)
                {
                    RadarItemEntity newItem = new RadarItemEntity();
                    newItem.setRadarInstance(radarEntity);
                    newItem.setDetails(assessmentItem.getDetails());
                    newItem.setRadarCategory(radarCategoryDAO.findOne(assessmentItem.getRadarCategory().getId()));
                    newItem.setRadarRing(radarRingDAO.findOne(assessmentItem.getRadarRing().getId()));
                    newItem.setConfidenceFactor(assessmentItem.getConfidenceFactor());
                    newItem.setState(assessmentItem.getState());

                    TechnologyEntity targetTechnology = null;

                    if(assessmentItem.getTechnology().getId() != null)
                    {
                        targetTechnology = technologyDAO.findOne(assessmentItem.getTechnology().getId());
                    }

                    if(targetTechnology == null)
                    {
                        targetTechnology = technologyDAO.findByName(assessmentItem.getTechnology().getName());
                    }

                    if(targetTechnology == null)
                    {
                        targetTechnology = new TechnologyEntity();
                        targetTechnology.setName(assessmentItem.getTechnology().getName());
                        targetTechnology.setCreator(assessmentItem.getTechnology().getCreator());
                        targetTechnology.setCreateDate(assessmentItem.getTechnology().getCreateDate());
                        targetTechnology.setUrl(assessmentItem.getTechnology().getUrl());
                        technologyDAO.save(targetTechnology);
                    }

                    newItem.setTechnology(targetTechnology);
                    radarItemDAO.save(newItem);

                    radarEntity.getRadarItems().add(newItem);
                }
            }
        }

        if(radarEntity != null)
        {
            this.entityRepository.save(radarEntity);
        }

        return this.modelMapper.map(radarEntity, Radar.class);
    }
}
