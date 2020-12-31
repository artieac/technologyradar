package com.pucksandprogramming.technologyradar.services;

import com.pucksandprogramming.technologyradar.data.repositories.RadarUserRepository;
import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.security.TechRadarSecurityPrincipal;
import com.pucksandprogramming.technologyradar.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public abstract class ServiceBase {
    private final RadarUserRepository radarUserRepository;

    public ServiceBase(RadarUserRepository radarUserRepository)
    {
        this.radarUserRepository = radarUserRepository;
    }

    public RadarUserRepository getRadarUserRepository() { return this.radarUserRepository; }

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
        Optional<AuthenticatedUser> authenticatedUser = this.getAuthenticatedUser();

        if(authenticatedUser.isPresent()){
            return authenticatedUser.get().getRadarUser();
        }

        return Optional.empty();
    }
}
