package com.pucksandprogramming.technologyradar.domainmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by acorrea on 12/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth0UserProfile {

    private boolean emailVerified;
    private String email;
    private String clientID;
    private String name;
    private String picture;
    private String userId;
    private String nickname;
    private String sub;
    private Date createdAt;
    private Date updatedAt;

    @JsonProperty(value="email_verified")
    public boolean getEmailVerified() { return this.emailVerified;}
    @JsonProperty(value="email_verified")
    public void setEmail_verified(boolean value) { this.emailVerified = value;}

    public String getEmail() { return this.email;}
    public void setEmail(String value) { this.email = value;}

    public String getClientID() { return this.clientID;}
    public void setClientID(String value) { this.clientID = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getPicture() { return this.picture;}
    public void setPicture(String value) { this.name = value;}

    @JsonProperty(value="user_id")
    public String getUserId() { return this.userId;}
    @JsonProperty(value="user_id")
    public void setUserId(String value) { this.name = value;}

    public String getNickname() { return this.nickname;}
    public void setNickname(String value) { this.nickname = value;}

    public String getSub() { return this.sub;}
    public void setSub(String value) { this.sub = value;}

    @JsonProperty(value="created_at")
    public Date getCreatedAt() { return this.createdAt;}
    @JsonProperty(value="created_at")
    public void setCreatedAt(Date value) { this.createdAt = value;}

    @JsonProperty(value="updated_at")
    public Date getUpdatedAt() { return this.updatedAt;}
    @JsonProperty(value="updated_at")
    public void setUpdatedAt(Date value) { this.updatedAt = value;}

}
