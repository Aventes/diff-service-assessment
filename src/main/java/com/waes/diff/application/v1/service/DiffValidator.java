package com.waes.diff.application.v1.service;

import com.waes.diff.application.v1.enums.EqualityStatus;
import com.waes.diff.application.v1.model.domain.ComparisionModel;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.model.domain.Left;
import com.waes.diff.application.v1.model.domain.Right;
import com.waes.diff.application.v1.model.event.ComparisionResultCreatedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.waes.diff.application.v1.enums.EqualityStatus.*;

/**
 * Provides functionality to validate Left and Right parts of comparision model and provide validation result
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DiffValidator implements ApplicationEventPublisherAware {

    @NonNull
    private final DiffService diffService;
    private ApplicationEventPublisher eventPublisher;

    /**
     * Validates Left part of {@link ComparisionModel} triggers {@link ComparisionResultCreatedEvent} in any case
     * doesn't throw any exceptions
     *
     * @param id   - ID of the model
     * @param left - an instance of {@link Left} side to be compared
     */
    public void validateLeft(final Long id, final Left left) {
        final ComparisionModel model = diffService.findById(id);

        eventPublisher.publishEvent(
                ComparisionResultCreatedEvent.builder()
                                             .source(this)
                                             .id(id)
                                             .payload(validate(left, model.getRight()))
                                             .build());
    }

    /**
     * Validates Right part of {@link ComparisionModel} triggers {@link ComparisionResultCreatedEvent} in any case
     * doesn't throw any exceptions
     *
     * @param id    - ID of the model
     * @param right - an instance of {@link Right} side to be compared
     */
    public void validateRight(final Long id, final Right right) {
        final ComparisionModel model = diffService.findById(id);

        eventPublisher.publishEvent(
                ComparisionResultCreatedEvent.builder()
                                             .source(this)
                                             .id(id)
                                             .payload(validate(model.getLeft(), right))
                                             .build());
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    private ComparisionResult validate(final Left left, final Right right) {
        if (Objects.isNull(left) || StringUtils.isEmpty(left.getValue())) {
            return createResult(LEFT_IS_NOT_PROVIDED);
        }

        if (Objects.isNull(right) || StringUtils.isEmpty(right.getValue())) {
            return createResult(RIGHT_IS_NOT_PROVIDED);
        }

        final String leftValue = encodeToString(left.getValue());
        final String rightValue = encodeToString(right.getValue());

        if (Objects.isNull(left.getValue())) {
            return createResult(LEFT_IS_NOT_PROVIDED);
        }

        if (Objects.isNull(right.getValue())) {
            return createResult(RIGHT_IS_NOT_PROVIDED);
        }

        final List<String> offsets = getOffsets(leftValue, rightValue);
        if (!CollectionUtils.isEmpty(offsets)) {
            return createResult(DATA_DIFFERENT)
                    .toBuilder()
                    .offsets(offsets)
                    .length(leftValue.length() > leftValue.length()
                            ? leftValue.length()
                            : rightValue.length())
                    .build();
        }

        return createResult(EQUAL);
    }

    private List<String> getOffsets(final String leftValue, final String rightValue) {

        final List<String> offsets = new ArrayList<>();
        final byte[] leftValueBytes = leftValue.getBytes();
        final byte[] rightValueBytes = rightValue.getBytes();

        for (int i = 0; i < leftValueBytes.length; i++) {
            byte data = (byte) (leftValueBytes[i] ^ rightValueBytes[i]);
            if (data != 0) {
                offsets.add(String.valueOf(data));
            }
        }

        Collections.sort(offsets);
        return offsets;
    }

    private String encodeToString(final String value) {
        if (Base64.isBase64(value)) {
            return value;
        }

        try {
            return Base64.encodeBase64String(value.getBytes(Charset.defaultCharset()));
        } catch (Exception e) {
            log.error("An Error Occurred during Encoding, e", e);
        }

        return null;
    }

    private ComparisionResult createResult(final EqualityStatus status) {
        return ComparisionResult.builder()
                                .status(status)
                                .build();
    }
}
