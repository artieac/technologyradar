package com.pucksandprogramming.technologyradar.domainmodel;

import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.List;

public class Role {
    public static final int RoleType_User = 0;
    public static final int RoleType_Admin = 1;

    private Integer id;
    private String name;
    private List<String> permissions;

    public static Role createRole(Integer roleType) {
        Role retVal = null;

        switch(roleType) {
            case RoleType_Admin:
                retVal = createAdminRole();
                break;
            default:
                retVal = createUserRole();
                break;
        }

        return retVal;
    }

    public static Role createAdminRole() {
        Role retVal = new Role();
        retVal.setId(RoleType_Admin);
        retVal.setName("ROLE_ADMIN");
        retVal.addPermission(Permissions.ManageRadarTemplates);
        retVal.addPermission(Permissions.ManageRadars);
        retVal.addPermission(Permissions.UserManagement);
        retVal.addPermission(Permissions.AllUserRadarManagement);

        return retVal;
    }

    public static Role createUserRole() {
        Role retVal = new Role();
        retVal.setId(RoleType_User);
        retVal.setName("ROLE_USER");
        retVal.addPermission(Permissions.ManageRadarTemplates);
        retVal.addPermission(Permissions.ManageRadars);

        return retVal;
    }

    public Role(){}

    public Integer getId() { return this.id;}
    public void setId(Integer value) { this.id = value;}

    public String getName() { return this.name;}
    public void setName(String value) { this.name = value;}

    public List<String> getPermissions() { return this.permissions;}
    public void addPermission(String permission) {
        if(this.permissions==null) {
            this.permissions = new ArrayList<>();
        }

        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }
}
