package com.waes.diff.application.v1.model.event;

import com.waes.diff.application.v1.model.domain.ComparisionResult;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import static com.waes.diff.application.v1.enums.EventType.COMPARISION_RESULT_EVENT;

/**
 * Defines an Comparision Result Created Event to be processed within Spring Application
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComparisionResultCreatedEvent extends AbstractComparisionEvent {

    private final Long id;
    private final ComparisionResult payload;

    @Builder(toBuilder = true)
    ComparisionResultCreatedEvent(final Object source,
                                  final Long id,
                                  final ComparisionResult payload) {
        super(source, COMPARISION_RESULT_EVENT);
        this.id = id;
        this.payload = payload;
    }
}
