package com.pucksandprogramming.technologyradar.security.jwt;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

import java.util.Date;
import java.util.Optional;

public class TechRadarJwt {
    Long userId;
    String subject;
    Optional<Date> expirationDate;

    public TechRadarJwt() {
        this.expirationDate = Optional.empty();
    }

    public TechRadarJwt(RadarUser radarUser) {
        this(radarUser, null);
    }

    public TechRadarJwt(RadarUser radarUser, Date expirationDate){
        this.userId = radarUser.getId();
        this.subject = radarUser.getName();
        this.expirationDate = Optional.ofNullable(expirationDate);
    }

    public Long getUserId() { return this.userId; }
    public void setUserId(Long value) { this.userId = value;}

    public String getSubject() { return this.subject;}
    public void setSubject(String value) { this.subject = value;}

    public Optional<Date> getExpirationDate() { return this.expirationDate;}
}
