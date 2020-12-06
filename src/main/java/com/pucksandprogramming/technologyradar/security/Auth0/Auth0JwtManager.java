package com.pucksandprogramming.technologyradar.security.Auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pucksandprogramming.technologyradar.security.IdentityProviderUser;
import com.pucksandprogramming.technologyradar.security.jwt.JwtManagerBase;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Auth0JwtManager extends JwtManagerBase {
    private static final Logger logger = Logger.getLogger(Auth0JwtManager.class);

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

            authenticatedUser.setUserEmail(this.getClaimAsString(auth0Jwt, Claims.EMAIL_CLAIM));
            authenticatedUser.setNickname(this.getClaimAsString(auth0Jwt, Claims.NICKNAME_CLAIM));
            authenticatedUser.setName(this.getClaimAsString(auth0Jwt, Claims.NAME_CLAIM));

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
