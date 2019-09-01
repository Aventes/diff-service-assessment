package com.waes.diff.application.v1.model.event;

import com.waes.diff.application.v1.enums.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Defines an Abstract Comparision Event to be processed within Spring Application
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
abstract class AbstractComparisionEvent extends ApplicationEvent {

    private final EventType eventType;

    AbstractComparisionEvent(final Object source,
                             final EventType eventType) {
        super(source);
        this.eventType = eventType;
    }
}
