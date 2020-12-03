package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.security.WebSecurityConfig;
import com.auth0.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
@ControllerAdvice
public class LoginController {
    @Value("${com.auth0.callbackUrl}")
    private String callbackLocation;

    @Value("${com.auth0.domain}")
    private String authDomain;

    @Value("${com.auth0.clientId}")
    private String authClientId;

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private WebSecurityConfig appConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", appConfig.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    protected String logout(final HttpServletRequest req) {
        logger.debug("Performing logout");
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
