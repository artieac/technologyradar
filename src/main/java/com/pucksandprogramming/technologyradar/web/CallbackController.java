package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarType;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.services.RadarType.AssociatedRadarTypeService;
import com.pucksandprogramming.technologyradar.services.RadarType.DefaultRadarTypeManager;
import com.pucksandprogramming.technologyradar.services.RadarType.RadarTypeServiceFactory;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
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
import java.util.List;

@SuppressWarnings("unused")
@Controller
public class CallbackController
{
    @Autowired
    private RadarUserService userService;

    @Autowired
    private RadarTypeServiceFactory radarTypeServiceFactory;

    @Autowired
    private AssociatedRadarTypeService associatedRadarTypeService;

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

            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.extractJWTDetails(JWT.decode(tokens.getIdToken()));

            RadarUser targetUser = this.userService.findByAuthenticationId(authenticatedUser.getAuthSubjectIdentifier());

            if(targetUser == null)
            {
                targetUser = this.userService.addUser(  authenticatedUser.getAuthSubjectIdentifier(),
                                                        authenticatedUser.getAuthority(),
                                                        authenticatedUser.getAuthTokenIssuer(),
                                                        authenticatedUser.getUserEmail(),
                                                        authenticatedUser.getNickname(),
                                                        authenticatedUser.getName());

                if(targetUser.getId() > 0)
                {
                    List<RadarType> defaultRadars = DefaultRadarTypeManager.getDefaultRadarTypes(this.radarTypeServiceFactory.getMostRecent());

                    for(RadarType radarType : defaultRadars)
                    {
                        this.associatedRadarTypeService.associatedRadarType(targetUser, radarType.getId(), radarType.getVersion(), true);
                    }
                }
            }

            if(targetUser != null && targetUser.getId() > 0)
            {
                Role userRole = Role.createRole(targetUser.getRoleId());
                authenticatedUser.addGrantedAuthority(userRole.getName());

                for(String permission : userRole.getPermissions())
                {
                    authenticatedUser.addGrantedAuthority(permission);
                }

                authenticatedUser.setUserId(targetUser.getId());

                // TBD< switch this to an interface rather than a specific instance type
                Auth0TokenAuthentication tokenAuth = new Auth0TokenAuthentication(authenticatedUser);
                SecurityContextHolder.getContext().setAuthentication(tokenAuth);
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