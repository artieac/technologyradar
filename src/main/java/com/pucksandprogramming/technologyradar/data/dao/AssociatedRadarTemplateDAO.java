package com.pucksandprogramming.technologyradar.data.dao;

import com.pucksandprogramming.technologyradar.data.Entities.AssociatedRadarTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;

@Repository
public interface AssociatedRadarTemplateDAO extends JpaRepository<AssociatedRadarTemplateEntity, Long> {
    AssociatedRadarTemplateEntity findByRadarUserIdAndRadarTemplateId(Long radarUserId, Long radarTemplateId);
}
