package com.waes.diff.application.v1.service.listeners;

import com.waes.diff.application.v1.model.event.RightSideCreatedEvent;
import com.waes.diff.application.v1.service.DiffValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Defines an Event Listener to listen to RIGHT Side event changes and delegates processing responsibilities to
 * specific services/processors
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Component
@RequiredArgsConstructor
public class RightSideComparisionEventListener implements ApplicationListener<RightSideCreatedEvent> {

    @NonNull
    private final DiffValidator diffValidator;

    @Override
    public void onApplicationEvent(final RightSideCreatedEvent event) {
        diffValidator.validateRight(event.getId(), event.getPayload());
    }
}
