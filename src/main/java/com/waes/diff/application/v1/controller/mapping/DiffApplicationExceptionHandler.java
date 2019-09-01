package com.waes.diff.application.v1.controller.mapping;

import com.waes.diff.application.v1.DiffSystemNotFoundException;
import com.waes.diff.application.v1.controller.model.StringResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Returns HTTP(404) - NOT_FOUND status when an instance of an entity cannot be found in the DB
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Slf4j
@ControllerAdvice
public class DiffApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link DiffSystemNotFoundException} and maps in to HTTP NON_FOUND status
     *
     * @param e          - an instacne of {@link DiffSystemNotFoundException} exception to be catched
     * @param webRequest - an instance of {@link WebRequest}
     * @return String Response with HTTP status NOT_FOUND and an appropriate reason
     */
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(DiffSystemNotFoundException.class)
    public final StringResponse handleNotFoundException(final DiffSystemNotFoundException e,
                                                        final WebRequest webRequest) {

        log.warn("Exception handled e={}, Not Found HTTP status is produced", e);

        return StringResponse.builder()
                             .response(e.getMessage())
                             .status(NOT_FOUND)
                             .build();
    }
}
