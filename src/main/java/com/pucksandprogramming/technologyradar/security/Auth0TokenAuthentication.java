package com.pucksandprogramming.technologyradar.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.*;

public class Auth0TokenAuthentication extends AbstractAuthenticationToken {

    private final DecodedJWT jwt;
    private boolean invalidated;
    private String userEmail;
    private String userNickname;
    private String name;

    public Auth0TokenAuthentication(DecodedJWT jwt) {
        super(readAuthorities(jwt));
        this.jwt = jwt;

        Claim emailClaim = jwt.getClaim("https://www.pucksandprogramming.com/email");
        this.setUserEmail(emailClaim.asString());
        Claim nicknameClaim = jwt.getClaim("https://www.pucksandprogramming.com/nickname");
        this.setUserNickname(nicknameClaim.asString());
        Claim nameClaim = jwt.getClaim("https://www.pucksandprogramming.com/name");
        this.setName(nameClaim.asString());
    }

    private boolean hasExpired() {
        return jwt.getExpiresAt().before(new Date());
    }

    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

    private static Collection<? extends GrantedAuthority> readAuthorities(DecodedJWT jwt) {
        Claim rolesClaim = jwt.getClaim("https://access.control/roles");
        if (rolesClaim.isNull()) {
            return Collections.emptyList();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] scopes = rolesClaim.asArray(String.class);
        for (String s : scopes) {
            SimpleGrantedAuthority a = new SimpleGrantedAuthority(s);
            if (!authorities.contains(a)) {
                authorities.add(a);
            }
        }
        return authorities;
    }

    @Override
    public String getCredentials() {
        return jwt.getToken();
    }

    @Override
    public Object getPrincipal() {
        return jwt.getSubject();
    }

    public String getUserEmail() { return this.userEmail;}
    public void setUserEmail(String value) { this.userEmail = value;}

    public String getUserNickname() { return this.userNickname;}
    public void setUserNickname(String value) { this.userNickname = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    private String identifier;

    public String getIdentifier()
    {
        String[] splitSubject = jwt.getSubject().toString().split("\\|");
        // using substring instead of split because its not splitting on | for some reason
        // even though I can see it is in it.
        return splitSubject[splitSubject.length - 1];
    }

    public String getAuthority() { return "auth0";}

    public String getIssuer() { return this.jwt.getIssuer();}

    public Object getPayload() { return jwt.getPayload();}

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Create a new Authentication object to authenticate");
        }
        invalidated = true;
    }

    @Override
    public boolean isAuthenticated() {
        boolean retVal = false;

        if (this.securityEnabled == true) {
            retVal = !invalidated && !hasExpired();
        } else {
            retVal = true;
        }

        return retVal;
    }
}

