package com.pucksandprogramming.technologyradar.web;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import com.pucksandprogramming.technologyradar.services.RadarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

/**
 * Created by acorrea on 12/26/2017.
 */
public class ControllerBase {
    @Value("${com.pucksandprogramming.securityEnabled}")
    private boolean securityEnabled;

    @Autowired
    private RadarUserService radarUserService;

    private RadarUser currentUser = null;
    private AuthenticatedUser authenticatedUser = null;

    public AuthenticatedUser getAuthenticatedUser() {
        if(this.authenticatedUser == null) {
            if(SecurityContextHolder.getContext().getAuthentication() instanceof TechRadarSecurityPrincipal) {
                TechRadarSecurityPrincipal tokenAuth = (TechRadarSecurityPrincipal) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null) {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
                else {
                    if(this.securityEnabled==false) {
                        Optional<RadarUser> radarUser = this.radarUserService.findOne(1L);
                        this.authenticatedUser= new AuthenticatedUser(radarUser.get());
                        TechRadarSecurityPrincipal oauthWrapper = new TechRadarSecurityPrincipal(this.authenticatedUser);
                        SecurityContextHolder.getContext().setAuthentication(oauthWrapper);
                    }
                }
            }
            else {
                if(this.securityEnabled==false) {
                    Optional<RadarUser> radarUser = this.radarUserService.findOne(1L);
                    this.authenticatedUser= new AuthenticatedUser(radarUser.get());
                    TechRadarSecurityPrincipal oauthWrapper = new TechRadarSecurityPrincipal(this.authenticatedUser);
                    SecurityContextHolder.getContext().setAuthentication(oauthWrapper);
                }
            }
        }

        return this.authenticatedUser;
    }

    public RadarUser getCurrentUser() {
        if(this.currentUser == null) {
             if(this.getAuthenticatedUser()!=null) {
                 this.currentUser = this.radarUserService.findOne(this.getAuthenticatedUser().getUserId()).get();
             }
        }

        return this.currentUser;
    }

    public boolean isCurrentUser(Long userId) {
        Optional<RadarUser> radarUser = radarUserService.findOne(userId);
        return this.isCurrentUser(radarUser);
    }

    public boolean isCurrentUser(Optional<RadarUser> radarUser){
        if (radarUser.isPresent() && radarUser.get().getId() == this.getCurrentUser().getId()) {
            return true;
        }

        return false;
    }

    public Long getCurrentUserId() {
        Long retVal = -1L;

        if(this.getCurrentUser()!=null) {
            retVal = this.getCurrentUser().getId();
        }

        return retVal;
    }

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("currentUserId", this.getCurrentUserId());
    }

}
