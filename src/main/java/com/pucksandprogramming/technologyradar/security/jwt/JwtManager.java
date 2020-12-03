package com.pucksandprogramming.technologyradar.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.security.SecretManager;
import org.bouncycastle.crypto.tls.SignatureAlgorithm;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtManager extends JwtManagerBase {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ISSUER = "pucksandprogramming.com";

    private class Claims{
        public static final String ID = "ID";
    };

    public JwtManager(SecretManager secretManager){
        super(secretManager);
    }

    public Optional<TechRadarJwt> getJwtDetails(String jwt){
        try {
            DecodedJWT auth0Jwt = JWT.decode(jwt);
            TechRadarJwt techRadarJwt = new TechRadarJwt();
            techRadarJwt.setUserId(this.getClaimAsLong(auth0Jwt, Claims.ID));
            return Optional.of(techRadarJwt);
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public String generateJwt(TechRadarJwt techRadarJwt) {
        String jwt = "";

        try {
            jwt = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(techRadarJwt.getSubject())
                    .withIssuedAt(new Date())
                    //                .withExpiresAt(new Date())
                    .withClaim(Claims.ID, techRadarJwt.getUserId())
                    .sign(Algorithm.HMAC256(this.getSecret()));
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }

        return jwt;
    }
}
