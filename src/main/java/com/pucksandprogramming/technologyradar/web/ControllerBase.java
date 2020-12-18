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
    @Autowired
    private RadarUserService radarUserService;

    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof TechRadarSecurityPrincipal) {
            TechRadarSecurityPrincipal tokenAuth = (TechRadarSecurityPrincipal) SecurityContextHolder.getContext().getAuthentication();

            if (tokenAuth != null) {
                return Optional.ofNullable(tokenAuth.getAuthenticatedUser());
            }
        }

        return Optional.empty();
    }

    public Optional<RadarUser> getCurrentUser() {
         if(this.getAuthenticatedUser().isPresent()) {
             return this.radarUserService.findOne(this.getAuthenticatedUser().get().getUserId());
         }

         return Optional.empty();
    }

    public boolean isCurrentUser(Long userId) {
        Optional<RadarUser> radarUser = radarUserService.findOne(userId);
        return this.isCurrentUser(radarUser);
    }

    public boolean isCurrentUser(Optional<RadarUser> radarUser){
        Optional<RadarUser> currentUser = this.getCurrentUser();

        if (radarUser.isPresent() && currentUser.isPresent() &&
                radarUser.get().getId() == currentUser.get().getId()) {
            return true;
        }

        return false;
    }

    public Long getCurrentUserId() {
        Long retVal = -1L;

        if(this.getCurrentUser().isPresent()) {
            retVal = this.getCurrentUser().get().getId();
        }

        return retVal;
    }

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("currentUserId", this.getCurrentUserId());
    }

}
