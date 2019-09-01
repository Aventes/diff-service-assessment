package com.waes.diff.application.v1.model.domain;

import lombok.Builder;
import lombok.Value;

/**
 * Defines a Right part of model of comparing two plain text values in Base 64 format
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@Builder
public class Right {
    private Long id;
    private String value;
}
