package com.pucksandprogramming.technologyradar.security.Auth0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Auth0Configuration {
    @Autowired
    Environment env;

    @Value("${com.auth0.domain}")
    private String domain;

    @Value("${com.auth0.clientId}")
    private String clientId;

    @Value("${com.auth0.clientSecret}")
    private String clientSecret;

    @Value("${com.auth0.callbackUrl}")
    private String callbackLocation;

    private boolean shouldUseEnvironmentForConfiguration() {
        boolean retVal = false;

        String propertyValue = env.getProperty("com.pucksandprogramming.useEnvironmentForConfiguration");

        retVal = Boolean.parseBoolean(propertyValue);

        return retVal;
    }

    public String getDomain() {
        String retVal = domain;

        if(this.shouldUseEnvironmentForConfiguration()) {
            retVal = env.getProperty("AUTH0_DOMAIN");
        }

        return retVal;
    }

    public String getClientId() {
        String retVal = clientId;

        if(this.shouldUseEnvironmentForConfiguration()) {
            retVal = env.getProperty("AUTH0_CLIENT_ID");
        }

        return retVal;
    }

    public String getClientSecret() {
        String retVal = clientSecret;

        if(this.shouldUseEnvironmentForConfiguration()) {
            retVal = env.getProperty("AUTH0_CLIENT_SECRET");
        }

        return retVal;
    }

    public String getCallbackLocation(){
        return this.callbackLocation;
    }
}
