package com.alwaysmoveforward.technologyradar.domainmodel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by acorrea on 12/23/2017.
 */
public class RadarUser {

    private Long id;

    private String authenticationId;

    private int roleId;

    public RadarUser()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getAuthenticationId() { return this.authenticationId;}

    public void setAuthenticationId(String value) { this.authenticationId = value;}

    public int getRoleId() { return this.roleId;}

    public void setRoleId(int value) { this.roleId = value;}
}
