package com.waes.diff.application.v1.controller;

import com.waes.diff.application.v1.controller.model.StringResponse;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.service.DiffService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * Controller that handles incoming HTTP request to the system
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Slf4j
@RestController
@RequestMapping("/v1/diff/{id}")
@RequiredArgsConstructor
public class DiffController {

    @NonNull
    private DiffService diffService;

    /**
     * Finds Comparision Result by Model ID
     *
     * @param id - ID of the model to search by
     * @return an instance of {@link ComparisionResult}
     */
    @GetMapping
    public ComparisionResult getComparisionResult(final @PathVariable Long id) {
        log.trace("getComparisionResult.E id: {}", id);

        final ComparisionResult result = diffService.findResultById(id);

        log.trace("getComparisionResult.X id: {}, comparisionResult: {}", id, result);
        return result;
    }

    /**
     * Processes Left Comparision part of the model to be compared
     *
     * @param id   - ID of the model
     * @param data - plain text data in Base 64 format
     * @return plain text Response in JSON format
     */
    @PostMapping(value = "/left",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public StringResponse saveLeft(final @PathVariable Long id,
                                   final @RequestBody String data) {

        log.trace("saveLeft.E id: {}, data: {}", id, data);

        diffService.saveLeft(id, data);

        log.trace("saveLeft.X id: {}, data: {}", id, data);
        return StringResponse.builder()
                             .response("Successfully Saved!")
                             .status(OK)
                             .build();
    }

    /**
     * Processes Right Comparision part of the model to be compared
     *
     * @param id   - ID of the model
     * @param data - plain text data in Base 64 format
     * @return plain text Response in JSON format
     */
    @PostMapping(value = "/right",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public StringResponse saveRight(final @PathVariable Long id,
                                    final @RequestBody String data) {

        log.trace("saveRight.E id: {}, data: {}", id, data);

        diffService.saveRight(id, data);

        log.trace("saveRight.X id: {}, data: {}", id, data);
        return StringResponse.builder()
                             .response("Successfully Saved!")
                             .status(OK)
                             .build();
    }
}
