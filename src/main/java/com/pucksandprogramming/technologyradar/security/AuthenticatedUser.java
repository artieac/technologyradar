package com.pucksandprogramming.technologyradar.security;

import com.pucksandprogramming.technologyradar.domainmodel.RadarUser;
import com.pucksandprogramming.technologyradar.domainmodel.Role;
import com.pucksandprogramming.technologyradar.domainmodel.UserType;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class AuthenticatedUser {
    private final Optional<RadarUser> radarUser;

    List<GrantedAuthority> grantedAuthorities;

    public AuthenticatedUser() {
        this.radarUser = Optional.empty();
    }

    public AuthenticatedUser(RadarUser radarUser){
        this(Optional.ofNullable(radarUser));
    }

    public AuthenticatedUser(Optional<RadarUser> radaruser) {
        this.radarUser = radaruser;

        if (this.radarUser.isPresent()) {
            Role userRole = Role.createRole(radaruser.get().getRoleId());
            this.addGrantedAuthority(userRole.getName());

            for (String permission : userRole.getPermissions()) {
                this.addGrantedAuthority(permission);
            }
        }
    }

    public Optional<RadarUser> getRadarUser() {
        return this.radarUser;
    }

    public String getAuthToken() {
        if (this.radarUser.isPresent()) {
            return this.radarUser.get().getAuthenticationId();
        }

        return "";
    }

    public String getAuthSubject() {
        if (this.radarUser.isPresent()) {
            return this.radarUser.get().getName();
        }

        return "";
    }

    public Date getAuthExpiration() {
        return new Date(2022, 1, 1);
    }

    public Long getUserId() {
        if (this.radarUser.isPresent()) {
            return this.radarUser.get().getId();
        }

        return -1L;
    }

    public List<GrantedAuthority> getGrantedAuthorities() { return this.grantedAuthorities;};

    public String getAuthSubjectIdentifier() {
        String[] splitSubject = this.getAuthSubject().split("\\|");
        // using substring instead of split because its not splitting on | for some reason
        // even though I can see it is in it.
        return splitSubject[splitSubject.length - 1];
    }

    public void addGrantedAuthority(String authorityName) {
        if(this.grantedAuthorities==null) {
            this.grantedAuthorities = new ArrayList<>();
        }

        SimpleGrantedAuthority newAuthority = new SimpleGrantedAuthority(authorityName);

        if(!this.grantedAuthorities.contains(newAuthority)) {
            this.grantedAuthorities.add(newAuthority);
        }
    }

    public boolean hasPrivilege(String userPrivilege) {
        boolean retVal = false;

        if(!userPrivilege.isEmpty() && this.grantedAuthorities != null) {
            for(GrantedAuthority grantedAuthority : this.getGrantedAuthorities()) {
                if(grantedAuthority.toString()==userPrivilege) {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }

    public boolean isAuthenticated() {
        if(this.radarUser.isPresent() && this.radarUser.get().getId() > 0){
            return true;
        }

        return false;
    }
}
