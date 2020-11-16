package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RadarAccessManager {
    public enum ViewAccessMode {
        PublishedOnly,
        FullAccess
    }

    AuthenticatedUser authenticatedUser;

    public AuthenticatedUser getAuthenticatedUser() {

        if(this.authenticatedUser == null) {

            if(SecurityContextHolder.getContext().getAuthentication() instanceof Auth0TokenAuthentication) {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null) {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
            }
        }

        return this.authenticatedUser;
    }

    public ViewAccessMode canViewHistory(RadarUser targetDataOwner) {
        ViewAccessMode retVal = ViewAccessMode.PublishedOnly;

        if(this.getAuthenticatedUser()!=null) {
            if (this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
                // is this person an admin?
                retVal = RadarAccessManager.ViewAccessMode.FullAccess;
            }
            else {
                if (targetDataOwner != null) {
                    if(targetDataOwner.getId() == this.getAuthenticatedUser().getUserId()) {
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

    public boolean canModifyRadar(RadarUser targetDataOwner) {
        boolean retVal = false;

        if(this.getAuthenticatedUser()!=null) {
            if(this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName())) {
                retVal = true;
            }
            else
            {
                if (targetDataOwner != null) {
                    // logged in user owns the data
                    if (targetDataOwner.getId() == this.getAuthenticatedUser().getUserId()) {
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
