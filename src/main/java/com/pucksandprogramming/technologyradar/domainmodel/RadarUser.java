package com.pucksandprogramming.technologyradar.domainmodel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

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
    private List<RadarType> radarTypes;

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

    public List<RadarType> getRadarTypes()
    {
        if(this.radarTypes == null)
        {
            this.radarTypes = new ArrayList<RadarType>();

        }
        return this.radarTypes;
    }

    public void setRadarTypes(List<RadarType> value) { this.radarTypes = value;}

    public void addRadarType(RadarType radarType)
    {
        if(radarType != null)
        {
            if(radarType.getId() > 0)
            {
                if(!this.updateRadarType(radarType))
                {
                    this.getRadarTypes().add(radarType);
                }
            }
            else
            {
                this.getRadarTypes().add(radarType);
            }
        }
    }

    public boolean updateRadarType(RadarType radarType)
    {
        boolean retVal = false;

        if(radarType!=null)
        {
            for(int i = 0; i < this.getRadarTypes().size(); i++)
            {
                if(this.getRadarTypes().get(i).getId()==radarType.getId())
                {
                    this.getRadarTypes().set(i, radarType);
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }
}
