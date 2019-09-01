package com.waes.diff.application.v1.model.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Defines a model of comparing two plain text values in Base 64 format
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@Builder(toBuilder = true)
public class ComparisionModel {
    private Long id;
    private Left left;
    private Right right;
    private ComparisionResult result;
}
