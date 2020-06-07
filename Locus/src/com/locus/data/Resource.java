/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import java.util.Set;
import java.util.stream.Stream;

public class Resource {

    private ResourceType resourceType;

    private Set<ActionType> actionType;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Set<ActionType> getActionType() {
        return actionType;
    }

    public void setActionType(Set<ActionType> actionType) {
        this.actionType = actionType;
    }

    public static interface ResourceTypeStep {
        ActionTypeStep withResourceType(ResourceType resourceType);
    }

    public static interface ActionTypeStep {
        BuildStep withActionType(Set<ActionType> actionType);
    }

    public static interface BuildStep {
        Resource build();
    }


    public static class Builder implements ResourceTypeStep, ActionTypeStep, BuildStep {
        private ResourceType resourceType;
        private Set<ActionType> actionType;

        private Builder() {
        }

        public static ResourceTypeStep resource() {
            return new Builder();
        }

        @Override
        public ActionTypeStep withResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        @Override
        public BuildStep withActionType(Set<ActionType> actionType) {
            this.actionType = actionType;
            return this;
        }

        @Override
        public Resource build() {
            Resource resource = new Resource();
            resource.setResourceType(this.resourceType);
            resource.setActionType(this.actionType);
            return resource;
        }
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceType=" + resourceType +
                ", actionType=" + actionType +
                '}';
    }
}

