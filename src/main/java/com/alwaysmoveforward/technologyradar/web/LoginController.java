package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.security.WebSecurityConfig;
import com.auth0.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
public class LoginController
{
    @Value("${com.auth0.callbackUrl}")
    private String callbackLocation;

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private WebSecurityConfig appConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req)
    {
        logger.debug("Performing login");
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + callbackLocation;
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
                .withAudience(String.format("https://%s/userinfo", appConfig.getDomain()))
                .build();
        return "redirect:" + authorizeUrl;
    }

}
