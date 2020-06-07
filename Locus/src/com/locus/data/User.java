/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import java.util.Set;

public class User {

    private String name;

    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static interface NameStep {
        RolesStep withName(String name);
    }

    public static interface RolesStep {
        BuildStep withRoles(Set<Role> roles);
    }

    public static interface BuildStep {
        User build();
    }


    public static class Builder implements NameStep, RolesStep, BuildStep {
        private String name;
        private Set<Role> roles;

        private Builder() {
        }

        public static NameStep user() {
            return new Builder();
        }

        @Override
        public RolesStep withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public BuildStep withRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        @Override
        public User build() {
            User user = new User();
            user.setName(this.name);
            user.setRoles(this.roles);
            return user;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
