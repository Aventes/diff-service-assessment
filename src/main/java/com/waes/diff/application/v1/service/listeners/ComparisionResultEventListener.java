package com.waes.diff.application.v1.service.listeners;

import com.waes.diff.application.v1.model.event.ComparisionResultCreatedEvent;
import com.waes.diff.application.v1.service.DiffService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Defines an Event Listener to listen to Comparision Result event changes and delegates processing
 * responsibilities to specific services/processors
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Component
@RequiredArgsConstructor
public class ComparisionResultEventListener implements ApplicationListener<ComparisionResultCreatedEvent> {

    @NonNull
    private final DiffService diffService;

    @Override
    public void onApplicationEvent(final ComparisionResultCreatedEvent event) {
        diffService.saveResult(event.getId(), event.getPayload());
    }
}
