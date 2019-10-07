package com.pucksandprogramming.technologyradar.services.RadarInstance;

import com.pucksandprogramming.technologyradar.data.repositories.TeamRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.Team;
import com.pucksandprogramming.technologyradar.security.Auth0TokenAuthentication;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RadarAccessManager
{
    public enum ViewAccessMode
    {
        PublishedOnly,
        FullAccess
    }

    AuthenticatedUser authenticatedUser;
    TeamRepository teamRepository;

    @Autowired
    public RadarAccessManager(TeamRepository teamRepository)
    {
        this.teamRepository = teamRepository;
    }

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

    public ViewAccessMode canViewUserRadars(RadarUser targetDataOwner)
    {
        ViewAccessMode retVal = ViewAccessMode.PublishedOnly;

        if(isOwnerOrAdmin(targetDataOwner))
        {
            retVal = ViewAccessMode.FullAccess;
        }

        return retVal;
    }

    public ViewAccessMode canViewRadar(RadarUser targetDataOwner, Long radarId)
    {
        ViewAccessMode retVal = ViewAccessMode.PublishedOnly;

        if(isOwnerOrAdmin(targetDataOwner))
        {
            retVal = ViewAccessMode.FullAccess;
        }
        else
        {
            if(this.isInTeam(radarId))
            {
                retVal = ViewAccessMode.FullAccess;
            }
        }

        return retVal;
    }

    private boolean isInTeam(Long radarId)
    {
        boolean retVal = true;

        if(this.getAuthenticatedUser()!=null)
        {
            List<Team> radarTeam = this.teamRepository.findByRadarAndMember(this.getAuthenticatedUser().getUserId(), radarId);

            if (radarTeam != null && radarTeam.size() > 0)
            {
                retVal = true;
            }
        }

        return retVal;
    }

    private boolean isOwnerOrAdmin(RadarUser targetDataOwner)
    {
        boolean retVal = false;

        if(this.getAuthenticatedUser()!=null)
        {
            if(this.getAuthenticatedUser().isAdmin())
            {
                retVal = true;
            }
            else
            {
                if (targetDataOwner != null)
                {
                    // logged in user owns the data
                    if (targetDataOwner.getId() == this.getAuthenticatedUser().getUserId())
                    {
                        retVal = true;
                    }
                }
            }
        }

        return retVal;
    }

    public boolean canAddRadar(RadarUser targetDataOwner)
    {
        return isOwnerOrAdmin(targetDataOwner);
    }

    public boolean canDeleteRadar(RadarUser targetDataOwner)
    {
        return isOwnerOrAdmin(targetDataOwner);
    }

    public boolean canModifyRadar(RadarUser targetDataOwner, Long radarId)
    {
        boolean retVal = isOwnerOrAdmin(targetDataOwner);

        if(retVal == false)
        {
            retVal = this.isInTeam(radarId);
        }

        return retVal;
    }
}
