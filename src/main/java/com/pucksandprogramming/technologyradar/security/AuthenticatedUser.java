package com.pucksandprogramming.technologyradar.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.*;

public class AuthenticatedUser
{
    private Long userId;
    private String name;
    private String userEmail;
    private String nickname;
    private String authToken;
    private String authSubject;
    private Date authExpiration;
    private String authTokenIssuer;

    List<GrantedAuthority> grantedAuthorities;

    public AuthenticatedUser()
    {

    }

    public String getAuthority() { return "auth0";}

    public Long getUserId() { return this.userId;}
    public void setUserId(Long value) { this.userId = value;}

    public String getName() { return this.name;}
    private void setName(String value) { this.name = value;}

    public String getUserEmail() { return this.userEmail;}
    private void setUserEmail(String value) { this.userEmail = value;}

    public String getNickname() { return this.nickname;}
    private void setNickname(String value) { this.nickname = value;}

    public String getAuthToken() { return this.authToken;}
    private void setAuthToken(String value) { this.authToken = value;}

    public String getAuthSubject() { return this.authSubject;}
    private void setAuthSubject(String value) { this.authSubject = value;}

    public Date getAuthExpiration() { return this.authExpiration;}
    private void setAuthExpiration(Date value) { this.authExpiration = value;}

    public String getAuthTokenIssuer() { return this.authTokenIssuer;}
    public void setAuthTokenIssuer(String value) { this.authTokenIssuer = value;}

    public List<GrantedAuthority> getGrantedAuthorities() { return this.grantedAuthorities;};

    public String getAuthSubjectIdentifier()
    {
        String[] splitSubject = this.getAuthSubject().split("\\|");
        // using substring instead of split because its not splitting on | for some reason
        // even though I can see it is in it.
        return splitSubject[splitSubject.length - 1];
    }

    public void extractJWTDetails(DecodedJWT jwt)
    {
        this.addGrantedAuthorities(jwt);
        this.extractClaimsDetails(jwt);

        this.setAuthToken(jwt.getToken());
        this.setAuthSubject(jwt.getSubject());
        this.setAuthExpiration(jwt.getExpiresAt());
        this.setAuthTokenIssuer(jwt.getIssuer());
    }

    public void addGrantedAuthority(String authorityName)
    {
        if(this.grantedAuthorities==null)
        {
            this.grantedAuthorities = new ArrayList<>();
        }

        SimpleGrantedAuthority newAuthority = new SimpleGrantedAuthority(authorityName);

        if(!this.grantedAuthorities.contains(newAuthority))
        {
            this.grantedAuthorities.add(newAuthority);
        }
    }

    public void addGrantedAuthorities(DecodedJWT jwt)
    {
        Claim rolesClaim = jwt.getClaim("https://access.control/roles");

        if (!rolesClaim.isNull())
        {
            List<GrantedAuthority> authorities = new ArrayList<>();
            String[] scopes = rolesClaim.asArray(String.class);
            for (String s : scopes)
            {
                this.addGrantedAuthority(s);
            }
        }
    }

    private void extractClaimsDetails(DecodedJWT jwt)
    {
        Claim emailClaim = jwt.getClaim("https://www.pucksandprogramming.com/email");
        this.setUserEmail(emailClaim.asString());
        Claim nicknameClaim = jwt.getClaim("https://www.pucksandprogramming.com/nickname");
        this.setName(nicknameClaim.asString());
        Claim nameClaim = jwt.getClaim("https://www.pucksandprogramming.com/name");
        this.setName(nameClaim.asString());
    }

}