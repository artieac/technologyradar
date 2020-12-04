package com.pucksandprogramming.technologyradar.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdentityProviderUser {
    private Long userId;
    private String name;
    private String userEmail;
    private String nickname;
    private String authToken;
    private String authSubject;
    private Date authExpiration;
    private String authTokenIssuer;
    List<GrantedAuthority> grantedAuthorities;

    public IdentityProviderUser() {

    }

    public String getAuthority() { return "auth0";}

    public Long getUserId() { return this.userId;}
    public void setUserId(Long value) { this.userId = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public String getUserEmail() { return this.userEmail;}
    public void setUserEmail(String value) { this.userEmail = value;}

    public String getNickname() { return this.nickname;}
    public void setNickname(String value) { this.nickname = value;}

    public String getAuthToken() { return this.authToken;}
    public void setAuthToken(String value) { this.authToken = value;}

    public String getAuthSubject() { return this.authSubject;}
    public void setAuthSubject(String value) { this.authSubject = value;}

    public Date getAuthExpiration() { return this.authExpiration;}
    public void setAuthExpiration(Date value) { this.authExpiration = value;}

    public String getAuthTokenIssuer() { return this.authTokenIssuer;}
    public void setAuthTokenIssuer(String value) { this.authTokenIssuer = value;}

    public List<GrantedAuthority> getGrantedAuthorities() { return this.grantedAuthorities;};

    public String getAuthSubjectIdentifier() {
        String[] splitSubject = this.getAuthSubject().split("\\|");
        // using substring instead of split because its not splitting on | for some reason
        // even though I can see it is in it.
        return splitSubject[splitSubject.length - 1];
    }

    public void addGrantedAuthority(String authorityName) {
        if(this.grantedAuthorities==null) {
            this.grantedAuthorities = new ArrayList<>();
        }

        SimpleGrantedAuthority newAuthority = new SimpleGrantedAuthority(authorityName);

        if(!this.grantedAuthorities.contains(newAuthority)) {
            this.grantedAuthorities.add(newAuthority);
        }
    }
}
