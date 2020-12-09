package com.pucksandprogramming.technologyradar.security.jwt;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtManager jwtManager;
    private final RadarUserService radarUserService;

    private static String AUTHORIZATION_HEADER = "Authorization";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtManager jwtManager, RadarUserService radarUserService) {
        super(authenticationManager);
        this.jwtManager = jwtManager;
        this.radarUserService = radarUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Optional<TechRadarJwt> techRadarJwt = this.getDetailsFromCookie(req);

        if (!techRadarJwt.isPresent()) {
            techRadarJwt = this.getDetailsFromAuthHeader(req);
        }

        if(techRadarJwt.isPresent()){
            Optional<RadarUser> radarUser = this.radarUserService.findOne(techRadarJwt.get().getUserId());

            if (radarUser.isPresent()) {
                TechRadarSecurityPrincipal securityPrincipal = new TechRadarSecurityPrincipal(radarUser.get());
                SecurityContextHolder.getContext().setAuthentication(securityPrincipal);
            }
        }

        chain.doFilter(req, res);
    }

    private Optional<TechRadarJwt> getDetailsFromCookie(HttpServletRequest request){
        Cookie authCookie = null;

        if(request != null && request.getCookies()!=null) {
            for (Cookie requestCookie : request.getCookies()) {
                if (requestCookie.getName().equals(JwtCookieManager.COOKIE_NAME)) {
                    authCookie = requestCookie;
                    break;
                }
            }
        }

        if (authCookie != null) {
            return this.jwtManager.getJwtDetails(authCookie.getValue());
        }

        return Optional.empty();
    }

    private Optional<TechRadarJwt> getDetailsFromAuthHeader(HttpServletRequest request){
        if(request!=null) {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (StringUtils.hasText(authHeader)){
                String[] bearerComponents = authHeader.split(" ");

                if (bearerComponents.length > 1) {
                    return this.jwtManager.getJwtDetails(bearerComponents[1]);
                }
            }
        }

        return Optional.empty();
    }
}