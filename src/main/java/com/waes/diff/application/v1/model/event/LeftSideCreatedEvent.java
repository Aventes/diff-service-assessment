package com.waes.diff.application.v1.model.event;

import com.waes.diff.application.v1.model.domain.Left;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import static com.waes.diff.application.v1.enums.EventType.LEFT_SIDE_COMPARISION_EVENT;

/**
 * Defines a Left Side Created Event to be processed within Spring Application
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LeftSideCreatedEvent extends AbstractComparisionEvent {
    private Long id;
    private Left payload;

    @Builder(toBuilder = true)
    public LeftSideCreatedEvent(final Object source, final Long id, final Left payload) {
        super(source, LEFT_SIDE_COMPARISION_EVENT);
        this.id = id;
        this.payload = payload;
    }
}
