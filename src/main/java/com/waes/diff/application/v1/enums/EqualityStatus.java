package com.waes.diff.application.v1.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a result of equality comparision between two string values
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
public enum EqualityStatus {
    EQUAL("Plain Text Values are equal!"),
    LEFT_IS_NOT_PROVIDED("Left part of the Comparision data is not provided"),
    RIGHT_IS_NOT_PROVIDED("Right part of the Comparision data is not provided"),
    DATA_DIFFERENT("Data size is different, see offset to get into to the comparision result");

    private final String value;

    EqualityStatus(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
