package com.waes.diff.application.v1.service.listeners;

import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.model.event.ComparisionResultCreatedEvent;
import com.waes.diff.application.v1.service.DiffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.waes.diff.application.v1.enums.EqualityStatus.EQUAL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link ComparisionResultEventListener}
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class ComparisionResultEventListenerTest {
    private static final Long DEFAULT_MODEL_ID = 1000L;

    @Mock
    private DiffService mockDiffService;

    @Captor
    private ArgumentCaptor<Long> eventIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<ComparisionResult> eventValueArgumentCaptor;

    @InjectMocks
    private ComparisionResultEventListener unit;

    @Test
    public void onApplicationEvent() {
        //GIVEN
        final ComparisionResult expectedEventValue = ComparisionResult.builder()
                                                                      .status(EQUAL)
                                                                      .build();
        final ComparisionResultCreatedEvent expectedEvent = ComparisionResultCreatedEvent.builder()
                                                                                         .source(mockDiffService)
                                                                                         .id(DEFAULT_MODEL_ID)
                                                                                         .payload(expectedEventValue)
                                                                                         .build();

        //WHEN
        unit.onApplicationEvent(expectedEvent);

        //THEN
        verify(mockDiffService, times(1))
                .saveResult(eventIdArgumentCaptor.capture(),
                            eventValueArgumentCaptor.capture());

        assertEquals(DEFAULT_MODEL_ID, eventIdArgumentCaptor.getValue());
        assertEquals(expectedEventValue, eventValueArgumentCaptor.getValue());
        assertEquals(EQUAL, eventValueArgumentCaptor.getValue()
                                                    .getStatus());
    }
}