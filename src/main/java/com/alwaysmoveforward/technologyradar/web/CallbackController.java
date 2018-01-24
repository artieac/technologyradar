package com.alwaysmoveforward.technologyradar.web;

import com.alwaysmoveforward.technologyradar.domainmodel.Auth0UserProfile;
import com.alwaysmoveforward.technologyradar.domainmodel.RadarUser;
import com.alwaysmoveforward.technologyradar.security.Auth0TokenAuthentication;
import com.alwaysmoveforward.technologyradar.services.RadarUserService;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
public class CallbackController
{
    @Autowired
    private RadarUserService userService;

    @Autowired
    private AuthenticationController controller;
    private final String redirectOnFail;
    private final String redirectOnSuccess;

    public CallbackController()
    {
        this.redirectOnFail = "/login";
        this.redirectOnSuccess = "/home/secureradar";
    }

    @RequestMapping(value = "/auth0callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
    {
        handleAuth0Callback(req, res);
    }

    @RequestMapping(value = "/auth0callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
    {
        handleAuth0Callback(req, res);
    }

    private void handleAuth0Callback(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        try
        {
            Tokens tokens = controller.handle(req);
            // TBD< switch this to an interface rather than a specific instance type
            Auth0TokenAuthentication tokenAuth = new Auth0TokenAuthentication(JWT.decode(tokens.getIdToken()));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);

            RadarUser targetUser = this.userService.findByAuthenticationId(tokenAuth.getIdentifier());

            if(targetUser == null)
            {
                this.userService.addUser(tokenAuth.getIdentifier(), tokenAuth.getAuthority(), tokenAuth.getIssuer(), tokenAuth.getUserEmail(), tokenAuth.getUserNickname(), tokenAuth.getName());
            }

            res.sendRedirect(redirectOnSuccess);
        }
        catch (AuthenticationException | IdentityVerificationException e)
        {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
    }
}