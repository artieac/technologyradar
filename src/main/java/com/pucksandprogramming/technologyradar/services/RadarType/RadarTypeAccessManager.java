package com.pucksandprogramming.technologyradar.services.RadarType;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserRights;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RadarTypeAccessManager
{
    AuthenticatedUser authenticatedUser;

    public AuthenticatedUser getAuthenticatedUser()
    {
        if(this.authenticatedUser == null)
        {
            if(SecurityContextHolder.getContext().getAuthentication() instanceof Auth0TokenAuthentication)
            {
                Auth0TokenAuthentication tokenAuth = (Auth0TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();

                if (tokenAuth != null)
                {
                    authenticatedUser = tokenAuth.getAuthenticatedUser();
                }
            }
        }

        return this.authenticatedUser;
    }

    public boolean canViewAllRadarTypes(RadarUser dataOwner)
    {
        boolean retVal = false;

        // no data owner, so we aren't getting any data anyway.
        if(dataOwner!=null && this.getAuthenticatedUser()!=null)
        {
            // the data owner is the logged in user.
            if (dataOwner.getId() == this.getAuthenticatedUser().getUserId())
            {
                retVal = true;
            }
            else
            {
                // if the person is logged in, then fine, otherwise you aren't able to view much.
                if(this.getAuthenticatedUser()!=null)
                {
                    if (this.getAuthenticatedUser().hasPrivilege(Role.createRole(Role.RoleType_Admin).getName()))
                    {
                        // the data owner is not the logged in user, but is an admin
                        retVal = true;
                    } else if (this.getAuthenticatedUser().hasPrivilege(UserRights.CanViewHistory))
                    {
                        // the data owner is not the logged in user, or an admin but can view history of others (subscribed)
                        retVal = true;
                    }
                }
            }
        }

        return retVal;
    }
}
