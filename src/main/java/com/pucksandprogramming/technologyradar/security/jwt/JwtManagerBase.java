package com.pucksandprogramming.technologyradar.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pucksandprogramming.technologyradar.security.SecretManager;

public class JwtManagerBase {
    public JwtManagerBase(){ }

    public String getClaimAsString(DecodedJWT jwt, String claim){
        Claim jwtClaim = jwt.getClaim(claim);

        if(!jwtClaim.isNull()){
            return jwtClaim.asString();
        }

        return "";
    }

    public Long getClaimAsLong(DecodedJWT jwt, String claim){
        Claim jwtClaim = jwt.getClaim(claim);

        if(!jwtClaim.isNull()){
            return jwtClaim.asLong();
        }

        return -1L;
    }
}
