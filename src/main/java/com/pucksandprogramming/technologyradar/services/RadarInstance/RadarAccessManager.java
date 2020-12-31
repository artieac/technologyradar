package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
public class RadarAccessManager {
    public enum ViewAccessMode {
        PublishedOnly,
        FullAccess
    }

    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof TechRadarSecurityPrincipal) {
            TechRadarSecurityPrincipal tokenAuth = (TechRadarSecurityPrincipal) SecurityContextHolder.getContext().getAuthentication();

            if (tokenAuth != null) {
                return Optional.ofNullable(tokenAuth.getAuthenticatedUser());
            }
        }

        return Optional.empty();
    }

    public ViewAccessMode canViewHistory(RadarUser targetDataOwner) {
        ViewAccessMode retVal = ViewAccessMode.PublishedOnly;

        if(this.getAuthenticatedUser().isPresent()) {
            if (this.getAuthenticatedUser().get().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
                // is this person an admin?
                retVal = RadarAccessManager.ViewAccessMode.FullAccess;
            }
            else {
                if (targetDataOwner != null) {
                    if(targetDataOwner.getId() == this.getAuthenticatedUser().get().getUserId()) {
                        retVal = ViewAccessMode.FullAccess;
                    }
                }
            }
        }

        return retVal;
    }

    public boolean canShareRadarTemplates(RadarUser targetDataOwner) {
        boolean retVal = false;

        if (targetDataOwner != null) {
            if (targetDataOwner.canShareRadarTemplates() == true) {
                // public access, but the target owner can share their full history publicly.
                retVal = true;
            }
        }

        return retVal;
    }

    public boolean canModifyRadar(RadarUser radarUser){
        return this.canModifyRadar(Optional.ofNullable(radarUser));
    }

    public boolean canModifyRadar(Optional<RadarUser> targetDataOwner) {
        boolean retVal = false;

        if(this.getAuthenticatedUser().isPresent()) {
            if(this.getAuthenticatedUser().get().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
                retVal = true;
            }
            else
            {
                if (targetDataOwner.isPresent()) {
                    // logged in user owns the data
                    if (targetDataOwner.get().getId() == this.getAuthenticatedUser().get().getUserId()) {
                        retVal = true;
                    }
                    else {
                        // if they are in the same team
                        // TBD
                    }
                }
            }
        }

        return retVal;
    }
}
