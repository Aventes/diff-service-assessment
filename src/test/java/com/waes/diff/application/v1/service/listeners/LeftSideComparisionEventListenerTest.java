package com.waes.diff.application.v1.service.listeners;

import com.waes.diff.application.v1.model.domain.Left;
import com.waes.diff.application.v1.model.event.LeftSideCreatedEvent;
import com.waes.diff.application.v1.service.DiffValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
public class LeftSideComparisionEventListenerTest {
    private static final String TEST_VALUE = "aa";
    private static final Long DEFAULT_MODEL_ID = 1000L;

    @Mock
    private DiffValidator mockDiffValidator;

    @Captor
    private ArgumentCaptor<Long> eventIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<Left> eventValueArgumentCaptor;

    @InjectMocks
    private LeftSideComparisionEventListener unit;

    @Test
    public void onApplicationEvent() {
        //GIVEN
        final Left expectedEventValue = Left.builder()
                                            .id(DEFAULT_MODEL_ID)
                                            .value(TEST_VALUE)
                                            .build();
        final LeftSideCreatedEvent expectedEvent = LeftSideCreatedEvent.builder()
                                                                       .source(mockDiffValidator)
                                                                       .id(DEFAULT_MODEL_ID)
                                                                       .payload(expectedEventValue)
                                                                       .build();

        //WHEN
        unit.onApplicationEvent(expectedEvent);

        //THEN
        verify(mockDiffValidator, times(1))
                .validateLeft(eventIdArgumentCaptor.capture(),
                              eventValueArgumentCaptor.capture());

        assertEquals(DEFAULT_MODEL_ID, eventIdArgumentCaptor.getValue());
        assertEquals(expectedEventValue, eventValueArgumentCaptor.getValue());
        assertEquals(TEST_VALUE, eventValueArgumentCaptor.getValue()
                                                         .getValue());
    }
}