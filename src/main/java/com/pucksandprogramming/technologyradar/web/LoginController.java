package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.security.Auth0.Auth0Configuration;
import com.pucksandprogramming.technologyradar.security.WebSecurityConfig;
import com.auth0.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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

    private final AuthenticationController authenticationController;
    private final WebSecurityConfig webSecurityConfig;
    private final Auth0Configuration auth0Configuration;

    @Autowired
    public LoginController(AuthenticationController authenticationController,
                           WebSecurityConfig webSecurityConfig,
                           Auth0Configuration auth0Configuration){
        this.authenticationController = authenticationController;
        this.webSecurityConfig = webSecurityConfig;
        this.auth0Configuration = auth0Configuration;
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

        redirectUri += this.auth0Configuration.getCallbackLocation();
        String authorizeUrl = this.authenticationController.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", this.auth0Configuration.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }
}
