package com.waes.diff.application.v1.service;

import com.waes.diff.application.v1.model.domain.ComparisionModel;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.model.domain.Left;
import com.waes.diff.application.v1.model.domain.Right;
import com.waes.diff.application.v1.model.event.ComparisionResultCreatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static com.waes.diff.application.v1.enums.EqualityStatus.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link DiffValidator}
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DiffValidatorTest {
    private static final Long DEFAULT_MODEL_ID = 1000L;

    @Mock
    private ApplicationEventPublisher mockPublisher;

    @Mock
    private DiffService mockDiffService;

    @Captor
    private ArgumentCaptor<ComparisionResultCreatedEvent> comparisionResultEventArgumentCaptor;

    @InjectMocks
    private DiffValidator unit;

    private ComparisionModel expectedModel;

    @Before
    public void setUp() {
        unit.setApplicationEventPublisher(mockPublisher);
        this.expectedModel = ComparisionModel.builder()
                                             .id(DEFAULT_MODEL_ID)
                                             .build();
    }

    @Test
    public void validateLeft_equals() {
        //GIVEN
        final String validValue = "aa";
        final Left left = Left.builder()
                              .id(DEFAULT_MODEL_ID)
                              .value(validValue)
                              .build();
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(EQUAL)
                                                                  .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .id(DEFAULT_MODEL_ID)
                                                                                 .right(Right.builder()
                                                                                             .id(DEFAULT_MODEL_ID)
                                                                                             .value(validValue)
                                                                                             .build())
                                                                                 .build());

        //WHEN
        unit.validateLeft(DEFAULT_MODEL_ID, left);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(EQUAL, capturedEvent.getPayload()
                                         .getStatus());
    }

    @Test
    public void validateLeft_left_is_not_provided() {
        //GIVEN
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(LEFT_IS_NOT_PROVIDED)
                                                                  .build();


        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .right(Right.builder()
                                                                                             .id(DEFAULT_MODEL_ID)
                                                                                             .value("aa")
                                                                                             .build())
                                                                                 .build());

        //WHEN
        unit.validateLeft(DEFAULT_MODEL_ID, null);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(LEFT_IS_NOT_PROVIDED, capturedEvent.getPayload()
                                                        .getStatus());
    }

    @Test
    public void validateLeft_value_is_not_provided() {
        //GIVEN
        final String validValue = "aa";
        final Left left = Left.builder()
                              .id(DEFAULT_MODEL_ID)
                              .build();
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(LEFT_IS_NOT_PROVIDED)
                                                                  .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .right(Right.builder()
                                                                                             .id(DEFAULT_MODEL_ID)
                                                                                             .value(validValue)
                                                                                             .build())
                                                                                 .build());

        //WHEN
        unit.validateLeft(DEFAULT_MODEL_ID, left);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(LEFT_IS_NOT_PROVIDED, capturedEvent.getPayload()
                                                        .getStatus());
    }

    @Test
    public void validateLeft_different_size() {
        //GIVEN
        final String validValue = "aa";
        final Left left = Left.builder()
                              .id(DEFAULT_MODEL_ID)
                              .value(validValue)
                              .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .right(Right.builder()
                                                                                             .id(DEFAULT_MODEL_ID)
                                                                                             .value("abc1")
                                                                                             .build())
                                                                                 .build());

        //WHEN
        unit.validateLeft(DEFAULT_MODEL_ID, left);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(DATA_DIFFERENT, capturedEvent.getPayload()
                                                  .getStatus());
        assertNotNull(capturedEvent.getPayload()
                                   .getOffsets());
        assertTrue(capturedEvent.getPayload()
                                .getLength() != 0);
    }

    @Test
    public void validateRight_equals() {
        //GIVEN
        final String validValue = "aa";
        final Right right = Right.builder()
                                 .id(DEFAULT_MODEL_ID)
                                 .value(validValue)
                                 .build();
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(EQUAL)
                                                                  .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .id(DEFAULT_MODEL_ID)
                                                                                 .left(Left.builder()
                                                                                           .id(DEFAULT_MODEL_ID)
                                                                                           .value(validValue)
                                                                                           .build())
                                                                                 .build());

        //WHEN
        unit.validateRight(DEFAULT_MODEL_ID, right);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(EQUAL, capturedEvent.getPayload()
                                         .getStatus());
    }

    @Test
    public void validateRight_right_is_not_provided() {
        //GIVEN
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(RIGHT_IS_NOT_PROVIDED)
                                                                  .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .id(DEFAULT_MODEL_ID)
                                                                                 .left(Left.builder()
                                                                                           .id(DEFAULT_MODEL_ID)
                                                                                           .value("aa")
                                                                                           .build())
                                                                                 .build());

        //WHEN
        unit.validateRight(DEFAULT_MODEL_ID, null);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(RIGHT_IS_NOT_PROVIDED, capturedEvent.getPayload()
                                                         .getStatus());
    }


    @Test
    public void validateRight_value_is_not_provided() {
        //GIVEN
        final String validValue = "aa";
        final Right right = Right.builder()
                                 .id(DEFAULT_MODEL_ID)
                                 .value(null)
                                 .build();
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(RIGHT_IS_NOT_PROVIDED)
                                                                  .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .left(Left.builder()
                                                                                           .id(DEFAULT_MODEL_ID)
                                                                                           .value(validValue)
                                                                                           .build())
                                                                                 .build());

        //WHEN
        unit.validateRight(DEFAULT_MODEL_ID, right);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(expectedResult, capturedEvent.getPayload());
        assertEquals(RIGHT_IS_NOT_PROVIDED, capturedEvent.getPayload()
                                                         .getStatus());
    }

    @Test
    public void validateRight_different_size() {
        //GIVEN
        final String validValue = "aa";
        final Right right = Right.builder()
                                 .id(DEFAULT_MODEL_ID)
                                 .value("abc1")
                                 .build();

        when(mockDiffService.findById(DEFAULT_MODEL_ID)).thenReturn(expectedModel.toBuilder()
                                                                                 .left(Left.builder()
                                                                                           .id(DEFAULT_MODEL_ID)
                                                                                           .value(validValue)
                                                                                           .build())
                                                                                 .build());

        //WHEN
        unit.validateRight(DEFAULT_MODEL_ID, right);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(comparisionResultEventArgumentCaptor.capture());


        final ComparisionResultCreatedEvent capturedEvent = comparisionResultEventArgumentCaptor.getValue();
        assertEquals(DEFAULT_MODEL_ID, capturedEvent.getId());
        assertEquals(DATA_DIFFERENT, capturedEvent.getPayload()
                                                  .getStatus());
        assertNotNull(capturedEvent.getPayload()
                                   .getOffsets());
        assertTrue(capturedEvent.getPayload()
                                .getLength() != 0);
    }
}