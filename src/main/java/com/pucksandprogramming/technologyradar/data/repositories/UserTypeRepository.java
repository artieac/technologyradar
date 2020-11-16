package com.pucksandprogramming.technologyradar.data.repositories;

import com.pucksandprogramming.technologyradar.data.Entities.UserTypeEntity;
import com.pucksandprogramming.technologyradar.data.dao.UserTypeDAO;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserTypeRepository extends SimpleDomainRepository<UserType, UserTypeEntity, UserTypeDAO, Integer> {
    @Autowired
    EntityManager entityManager;

    @Autowired
    public void UserTypeRepository(UserTypeDAO entityRepository)
    {
        super.setEntityRepository(entityRepository);
    }

    public UserTypeRepository()
    {
        super(UserType.class);
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
    protected UserTypeEntity findOne(UserType domainModel) {
        return this.entityRepository.findOne(domainModel.getId());
    }
}
