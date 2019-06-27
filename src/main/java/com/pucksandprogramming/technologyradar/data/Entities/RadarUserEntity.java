package com.pucksandprogramming.technologyradar.data.Entities;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "Authority", nullable=false, length = 256)
    private String authority;

    @Column(name = "Issuer", nullable=false, length = 1024)
    private String issuer;

    @Column(name="Email", nullable=false, length=512)
    private String email;

    @Column(name="Nickname", nullable=false, length=512)
    private String nickname;

    @Column(name="Name", nullable=false, length=512)
    private String name;

    @Column(name="UserType", nullable=false)
    private Long userType;

    public RadarUserEntity()
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

    public Long getUserType(){ return this.userType;}
    public void setUserType(Long value){ this.userType = value;}
}
