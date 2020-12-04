package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.UserTypeEntity;
import com.pucksandprogramming.technologyradar.data.dao.TechnologyDAO;
import com.pucksandprogramming.technologyradar.data.dao.UserTypeDAO;
import com.pucksandprogramming.technologyradar.data.mapper.RadarMapper;
import com.pucksandprogramming.technologyradar.domainmodel.Technology;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserTypeRepository extends SimpleDomainRepository<UserType, UserTypeEntity, UserTypeDAO, Integer> {
    @Autowired
    public UserTypeRepository(RadarMapper modelMapper,
                              UserTypeDAO entityManager) {
        super(modelMapper, entityManager, UserType.class);
    }

    private List<UserType> mapList(List<UserTypeEntity> source) {
        List<UserType> retVal = new ArrayList<>();

        if (source != null) {
            for (int i = 0; i < source.size(); i++) {
                UserType newItem = this.modelMapper.map(source.get(i), UserType.class);
                retVal.add(newItem);
            }
        }

        return retVal;
    }

    @Override
    protected Optional<UserTypeEntity> findOne(UserType domainModel) {
        return this.entityRepository.findById(domainModel.getId());
    }
}
