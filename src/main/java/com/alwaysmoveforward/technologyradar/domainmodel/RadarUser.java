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

    private String authority;

    private String issuer;

    private String email;

    private String nickname;

    public RadarUser()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getAuthenticationId() { return this.authenticationId;}

    public void setAuthenticationId(String value) { this.authenticationId = value;}

    public int getRoleId() { return this.roleId;}

    public void setRoleId(int value) { this.roleId = value;}

    public String getAuthority() { return this.authority;}

    public void setAuthority(String value) { this.authority = value;}

    public String getIssuer() { return this.issuer;}

    public void setIssuer(String value) { this.issuer = value;}

    public String getEmail() { return this.email;}

    public void setEmail(String value) { this.email = value;}

    public String getNickname() { return this.nickname;}

    public void setNickname(String value) { this.nickname = value;}
}
