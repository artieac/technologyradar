package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarTemplate;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.security.IdentityProviderUser;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.security.Auth0.Auth0JwtManager;
import com.pucksandprogramming.technologyradar.security.jwt.JwtCookieManager;
import com.pucksandprogramming.technologyradar.security.jwt.TechRadarJwt;
import com.pucksandprogramming.technologyradar.services.RadarTemplate.AssociatedRadarTemplateService;
import com.pucksandprogramming.technologyradar.services.RadarTemplate.DefaultRadarTemplateManager;
import com.pucksandprogramming.technologyradar.services.RadarTemplate.RadarTemplateService;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Controller
@RequestScope
@ControllerAdvice
public class CallbackController {
    private static final Logger logger = Logger.getLogger(CallbackController.class);

    private final RadarUserService userService;
    private final RadarTemplateService radarTemplateService;
    private final AuthenticationController authentiationController;
    private final AssociatedRadarTemplateService associatedRadarTemplateService;
    private final Auth0JwtManager auth0JwtManager;
    private final JwtCookieManager jwtCookieManager;

    private final String redirectOnFail;
    private final String redirectOnSuccess;

    @Autowired
    public CallbackController(RadarUserService radarUserService,
                              RadarTemplateService radarTemplateService,
                              AuthenticationController authentiationController,
                              AssociatedRadarTemplateService associatedRadarTemplateService,
                              Auth0JwtManager auth0JwtManager,
                              JwtCookieManager jwtCookieManager) {
        this.userService = radarUserService;
        this.radarTemplateService = radarTemplateService;
        this.authentiationController = authentiationController;
        this.associatedRadarTemplateService = associatedRadarTemplateService;
        this.auth0JwtManager = auth0JwtManager;
        this.jwtCookieManager = jwtCookieManager;

        this.redirectOnFail = "/login";
        this.redirectOnSuccess = "/home/secureradar";
    }

    @RequestMapping(value = "/auth0callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handleAuth0Callback(req, res);
    }

    @RequestMapping(value = "/auth0callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handleAuth0Callback(req, res);
    }

    private void handleAuth0Callback(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Tokens tokens = this.authentiationController.handle(req);

            Optional<IdentityProviderUser> identityProviderUser = auth0JwtManager.getAuthenticatedUser(tokens.getIdToken());

            if(identityProviderUser.isPresent()) {
                Optional<RadarUser> targetUser = this.userService.findByAuthenticationId(identityProviderUser.get().getAuthSubjectIdentifier());

                if (!targetUser.isPresent()) {
                    targetUser = this.userService.addUser(identityProviderUser.get().getAuthSubjectIdentifier(),
                            identityProviderUser.get().getAuthority(),
                            identityProviderUser.get().getAuthTokenIssuer(),
                            identityProviderUser.get().getUserEmail(),
                            identityProviderUser.get().getNickname(),
                            identityProviderUser.get().getName());

                    if (targetUser.get().getId() > 0) {
                        List<RadarTemplate> defaultRadars = DefaultRadarTemplateManager.getDefaultRadarTemplates(radarTemplateService);

                        for (RadarTemplate radarTemplate : defaultRadars) {
                            this.associatedRadarTemplateService.associateRadarTemplate(Optional.ofNullable(targetUser.get()), radarTemplate.getId(), true);
                        }
                    }
                }

                if (targetUser.isPresent() && targetUser.get().getId() > 0) {
                    AuthenticatedUser authenticatedUser = new AuthenticatedUser(targetUser);

                    // TBD< switch this to an interface rather than a specific instance type
                    TechRadarSecurityPrincipal securityPrincipal = new TechRadarSecurityPrincipal(authenticatedUser);
                    SecurityContextHolder.getContext().setAuthentication(securityPrincipal);
                    res.addCookie(this.jwtCookieManager.generateCookie(new TechRadarJwt(targetUser.get(), identityProviderUser.get().getAuthExpiration())));
                }
            }

            res.sendRedirect(redirectOnSuccess);
        }
        catch (AuthenticationException | IdentityVerificationException e) {
            logger.error(e);

            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
        catch (Exception e) {
            logger.error(e);

            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
    }
}