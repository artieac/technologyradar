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

    private boolean invalidated;
    private final AuthenticatedUser authenticatedUser;

    public Auth0TokenAuthentication(AuthenticatedUser authenticatedUser)
    {
        super(authenticatedUser.getGrantedAuthorities());

        this.authenticatedUser = authenticatedUser;
    }

    private boolean hasExpired()
    {
        return this.authenticatedUser.getAuthExpiration().before(new Date());
    }

    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

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
        invalidated = true;
    }

    @Override
    public boolean isAuthenticated() {
        boolean retVal = false;

        if (this.securityEnabled == true)
        {
            retVal = !invalidated && !hasExpired();
        }
        else
        {
            retVal = true;
        }

        return retVal;
    }
}

