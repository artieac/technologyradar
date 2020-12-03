package com.pucksandprogramming.technologyradar.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.*;

public class TechRadarSecurityPrincipal extends AbstractAuthenticationToken {
    private final AuthenticatedUser authenticatedUser;

    public TechRadarSecurityPrincipal(RadarUser radarUser){
        this(new AuthenticatedUser(radarUser));
    }

    public TechRadarSecurityPrincipal(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser.getGrantedAuthorities());

        this.authenticatedUser = authenticatedUser;
    }

    private boolean hasExpired()
    {
        return this.authenticatedUser.getAuthExpiration().before(new Date());
    }

    public AuthenticatedUser getAuthenticatedUser() { return this.authenticatedUser;}

    @Override
    public String getCredentials() {
        return this.authenticatedUser.getAuthToken();
    }

    @Override
    public Object getPrincipal() {
        return this.authenticatedUser.getAuthSubject();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Create a new Authentication object to authenticate");
        }
    }

    @Override
    public boolean isAuthenticated() {
        if(this.authenticatedUser!=null &&
                this.authenticatedUser.getRadarUser().isPresent() &&
                this.authenticatedUser.getRadarUser().get().getId() > 0){
            return true;
        }

        return false;
    }
}

