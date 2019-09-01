package com.waes.diff.application.v1.service.listeners;

import com.waes.diff.application.v1.model.event.LeftSideCreatedEvent;
import com.waes.diff.application.v1.service.DiffValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Defines an Event Listener to listen to LEFT Side event changes and delegates processing
 * responsibilities to specific services/processors
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Component
@RequiredArgsConstructor
public class LeftSideComparisionEventListener implements ApplicationListener<LeftSideCreatedEvent> {

    @NonNull
    private final DiffValidator diffValidator;

    @Override
    public void onApplicationEvent(final LeftSideCreatedEvent event) {
        diffValidator.validateLeft(event.getId(), event.getPayload());
    }
}
