/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import com.locus.exception.CustomException;

import java.util.stream.Stream;

/**
 * Assumption : For example there are 3 different types of resources in an ecommerce company,
 * a particular user can have specific accesses to each/any of them.
 */
public enum ResourceType {
    SHIPMENT,
    DISPATCH,
    ORDER;

    public static ResourceType getResourceTypeByName(String resourceTypeString) {
        return Stream.of(ResourceType.values()).filter(t -> t.name().equalsIgnoreCase(resourceTypeString))
                .findFirst().orElse(null);
    }
}
