package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.security.WebSecurityConfig;
import com.auth0.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
@Controller
@ControllerAdvice
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${com.auth0.callbackUrl}")
    private String callbackLocation;

    @Value("${com.auth0.domain}")
    private String authDomain;

    @Value("${com.auth0.clientId}")
    private String authClientId;

    private final AuthenticationController authenticationController;
    private final WebSecurityConfig webSecurityConfig;

    @Autowired
    public LoginController(AuthenticationController authenticationController,
                           WebSecurityConfig webSecurityConfig){
        this.authenticationController = authenticationController;
        this.webSecurityConfig = webSecurityConfig;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        logger.debug("Performing login");

        String redirectUri = req.getScheme() + "://" + req.getServerName();

        String serverPort = "";

        if(req.getServerPort()!=80 && req.getServerPort()!=443) {
            serverPort = Integer.toString(req.getServerPort());
            redirectUri += ":" + serverPort;
        }

        redirectUri += callbackLocation;
        String authorizeUrl = this.authenticationController.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", this.webSecurityConfig.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    protected String logout(final HttpServletRequest req, final HttpServletResponse response) {
        logger.debug("Performing logout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, response, auth);
        }
        invalidateSession(req);
        String returnTo = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
        String logoutUrl = String.format("https://%s/v2/logout?client_id=%s&returnTo=%s", authDomain, authClientId, returnTo);
        return "redirect:" + logoutUrl;
    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

}
