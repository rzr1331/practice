/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import java.util.Set;

public class Role {

    private RoleName roleName;

    private Set<Resource> resources;

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public static interface RoleNameStep {
        ResourcesStep withRoleName(RoleName roleName);
    }

    public static interface ResourcesStep {
        BuildStep withResources(Set<Resource> resources);
    }

    public static interface BuildStep {
        Role build();
    }

    public static class Builder implements RoleNameStep, ResourcesStep, BuildStep {
        private RoleName roleName;
        private Set<Resource> resources;

        private Builder() {
        }

        public static RoleNameStep role() {
            return new Builder();
        }

        @Override
        public ResourcesStep withRoleName(RoleName roleName) {
            this.roleName = roleName;
            return this;
        }

        @Override
        public BuildStep withResources(Set<Resource> resources) {
            this.resources = resources;
            return this;
        }

        @Override
        public Role build() {
            Role role = new Role();
            role.setRoleName(this.roleName);
            role.setResources(this.resources);
            return role;
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName=" + roleName +
                ", resources=" + resources +
                '}';
    }
}
