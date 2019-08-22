package com.pucksandprogramming.technologyradar.domainmodel;

/**
 * Created by acorrea on 12/23/2017.
 */
public class RadarUser {

    private Long id;
    private int roleId;
    private String authenticationId;
    private String authority;
    private String issuer;
    private String email;
    private String nickname;
    private String name;
    private UserType userType;

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

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public UserType getUserType(){ return this.userType;}
    public void setUserType(UserType value){ this.userType = value;}

    public boolean canVersionRadarTypes() { return this.userType.isGrantEnabled(UserRights.CanVersionRadarTypes);}

    public boolean canShareRadarTypes()
    {
        return this.userType.isGrantEnabled(UserRights.CanShareRadarTypes);
    }

    public int howManyRadarsCanShare()
    {
        return this.userType.getGrantValue(UserRights.CanShareNRadars);
    }

    public boolean canHaveVariableRadarRingCounts()
    {
        return this.userType.isGrantEnabled(UserRights.AllowVariableRadarRingCount);
    }
}
