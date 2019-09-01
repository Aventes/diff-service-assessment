package com.waes.diff.application.v1.model.event;

import com.waes.diff.application.v1.model.domain.Right;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import static com.waes.diff.application.v1.enums.EventType.RIGHT_SIDE_COMPARISION_EVENT;

/**
 * Defines a Right Side Created Event to be processed within Spring Application
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RightSideCreatedEvent extends AbstractComparisionEvent {
    private Long id;
    private Right payload;

    @Builder(toBuilder = true)
    public RightSideCreatedEvent(final Object source,
                                 final Long id,
                                 final Right payload) {

        super(source, RIGHT_SIDE_COMPARISION_EVENT);
        this.id = id;
        this.payload = payload;
    }
}
