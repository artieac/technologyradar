package com.pucksandprogramming.technologyradar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretManager {
    @Value("${com.pucksandprogramming.cookieSecret}")
    private String cookieSecret;

    @Autowired
    public SecretManager() {

    }

    public String getCookieSecret() {
        return this.cookieSecret;
    }
}
