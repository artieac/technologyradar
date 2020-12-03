package com.pucksandprogramming.technologyradar.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pucksandprogramming.technologyradar.security.IdentityProviderUser;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Auth0JwtManager {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private class Claims{
        public static final String EMAIL_CLAIM = "https://www.pucksandprogramming.com/email";
        public static final String NICKNAME_CLAIM="https://www.pucksandprogramming.com/nickname";
        public static final String NAME_CLAIM = "https://www.pucksandprogramming.com/name";
        public static final String ROLES_CLAIM = "https://access.control/roles";
    };

    public Auth0JwtManager() {}

    public Optional<IdentityProviderUser> getAuthenticatedUser(String jwt){
        try{
            DecodedJWT auth0Jwt = JWT.decode(jwt);

            IdentityProviderUser authenticatedUser = new IdentityProviderUser();

            Claim emailClaim = auth0Jwt.getClaim(Claims.EMAIL_CLAIM);
            authenticatedUser.setUserEmail(emailClaim.asString());

            Claim nicknameClaim = auth0Jwt.getClaim(Claims.NICKNAME_CLAIM);
            authenticatedUser.setNickname(nicknameClaim.asString());

            Claim nameClaim = auth0Jwt.getClaim(Claims.NAME_CLAIM);
            authenticatedUser.setName(nameClaim.asString());

            authenticatedUser.setAuthToken(auth0Jwt.getToken());
            authenticatedUser.setAuthSubject(auth0Jwt.getSubject());
            authenticatedUser.setAuthExpiration(auth0Jwt.getExpiresAt());
            authenticatedUser.setAuthTokenIssuer(auth0Jwt.getIssuer());

            Claim rolesClaim = auth0Jwt.getClaim(Claims.ROLES_CLAIM);

            if (!rolesClaim.isNull()) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                String[] scopes = rolesClaim.asArray(String.class);

                for (String s : scopes) {
                    authenticatedUser.addGrantedAuthority(s);
                }
            }
            return Optional.of(authenticatedUser);
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }
}
