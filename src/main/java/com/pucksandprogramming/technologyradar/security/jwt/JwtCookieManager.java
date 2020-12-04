package com.pucksandprogramming.technologyradar.security.jwt;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class JwtCookieManager {
    private final JwtManager jwtManager;

    public static final String COOKIE_NAME="TechRadarCookie";

    @Autowired
    public JwtCookieManager(JwtManager jwtManager){
        this.jwtManager = jwtManager;
    }

    public Cookie generateCookie(TechRadarJwt techRadarJwt){
        Cookie retVal = new Cookie(COOKIE_NAME, this.jwtManager.generateJwt(techRadarJwt));
 //       retVal.setDomain("");
        retVal.setHttpOnly(true);
 //       retVal.setMaxAge();

        return retVal;
    }
}
