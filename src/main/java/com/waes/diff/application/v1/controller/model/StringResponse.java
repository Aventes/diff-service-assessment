package com.waes.diff.application.v1.controller.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * A specific type of HTTP Response that defines only String value to be responded
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@Builder
public class StringResponse {
    private String response;
    private HttpStatus status;
}
