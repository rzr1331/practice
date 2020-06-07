/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import com.locus.exception.CustomException;

import java.util.stream.Stream;

/**
 * Assumption : 3 Specific roles : SALES, OPERATIONS, FINANCE
 */
public enum RoleName {
    SALES,
    OPERATIONS,
    FINANCE;

    public static RoleName getRoleName(String roleTypeString) {
        return Stream.of(RoleName.values()).filter(t -> t.name().equalsIgnoreCase(roleTypeString))
                .findFirst().orElse(null);
    }
}
