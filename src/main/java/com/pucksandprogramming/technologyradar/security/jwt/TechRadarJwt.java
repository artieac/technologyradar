package com.pucksandprogramming.technologyradar.security.jwt;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;

public class TechRadarJwt {
    Long userId;
    String subject;

    public TechRadarJwt() {

    }

    public TechRadarJwt(RadarUser radarUser){
        this.userId = radarUser.getId();
        this.subject = radarUser.getName();
    }

    public Long getUserId() { return this.userId; }
    public void setUserId(Long value) { this.userId = value;}

    public String getSubject() { return this.subject;}
    public void setSubject(String value) { this.subject = value;}
}
