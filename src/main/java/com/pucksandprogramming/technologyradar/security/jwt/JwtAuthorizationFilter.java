package com.pucksandprogramming.technologyradar.security.jwt;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private JwtManager jwtManager;
    private RadarUserService radarUserService;

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    private JwtManager getJwtManager(HttpServletRequest request) {
        if (this.jwtManager == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.jwtManager = webApplicationContext.getBean(JwtManager.class);
        }

        return this.jwtManager;
    }

    private RadarUserService getRadarUserService(HttpServletRequest request) {
        if (this.radarUserService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.radarUserService = webApplicationContext.getBean(RadarUserService.class);
        }

        return this.radarUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Cookie authCookie = null;

        for (Cookie requestCookie : req.getCookies()) {
            if (requestCookie.getName().equals(JwtCookieManager.COOKIE_NAME)) {
                authCookie = requestCookie;
                break;
            }
        }

        if (authCookie != null) {
            Optional<TechRadarJwt> techRadarJwt = this.getJwtManager(req).getJwtDetails(authCookie.getValue());

            if (techRadarJwt.isPresent()) {
                Optional<RadarUser> radarUser = this.getRadarUserService(req).findOne(techRadarJwt.get().getUserId());

                if (radarUser.isPresent()) {
                    TechRadarSecurityPrincipal securityPrincipal = new TechRadarSecurityPrincipal(radarUser.get());
                    SecurityContextHolder.getContext().setAuthentication(securityPrincipal);
                }
            }
        }

        chain.doFilter(req, res);
    }
}