package com.alwaysmoveforward.technologyradar.data.Entities;

import javax.persistence.*;

/**
 * Created by acorrea on 12/23/2017.
 */
@Entity
@Table(name = "RadarUser")
public class RadarUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "AuthenticationId", nullable=false, length=512)
    private String authenticationId;

    @Column(name = "RoleId", nullable=false)
    private int roleId;

    public RadarUserEntity()
    {

    }

    public Long getId(){ return this.id;}

    public void setId(Long value){ this.id = value;}

    public String getAuthenticationId() { return this.authenticationId;}

    public void setAuthenticationId(String value) { this.authenticationId = value;}

    public int getRoleId() { return this.roleId;}

    public void setRoleId(int value) { this.roleId = value;}
}
