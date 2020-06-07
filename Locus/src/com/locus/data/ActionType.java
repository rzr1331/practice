/**
 * Author : rzr1331
 * Date : 07/06/20
 */
package com.locus.data;

import java.util.stream.Stream;

public enum ActionType {
    READ,
    WRITE,
    DELETE;

    public static ActionType getActionTypeByName(String actionTypeString) {
        return Stream.of(ActionType.values()).filter(t -> t.name().equalsIgnoreCase(actionTypeString))
                .findFirst().orElse(null);
    }
}

