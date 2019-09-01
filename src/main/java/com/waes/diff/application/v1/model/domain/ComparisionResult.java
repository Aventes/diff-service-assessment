package com.waes.diff.application.v1.model.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waes.diff.application.v1.enums.EqualityStatus;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Defines a result of comparing two plain text values with equality status and list of discrepancies
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
public class ComparisionResult {
    private EqualityStatus status;
    private List<String> offsets;
    private int length;

    @Builder(toBuilder = true)
    @JsonCreator
    public ComparisionResult(final @JsonProperty("status") EqualityStatus status,
                             final @JsonProperty("offsets")  List<String> offsets,
                             final @JsonProperty("length") int length) {
        this.status = status;
        this.offsets = offsets;
        this.length = length;
    }
}
