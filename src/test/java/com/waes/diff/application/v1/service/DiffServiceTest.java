package com.waes.diff.application.v1.service;

import com.waes.diff.application.v1.model.domain.ComparisionModel;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.model.domain.Left;
import com.waes.diff.application.v1.model.domain.Right;
import com.waes.diff.application.v1.model.event.LeftSideCreatedEvent;
import com.waes.diff.application.v1.model.event.RightSideCreatedEvent;
import com.waes.diff.application.v1.repository.DiffRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static com.waes.diff.application.v1.enums.EqualityStatus.EQUAL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link DiffService}
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    private static final String TEST_LEFT_VALUE = "a1";
    private static final String TEST_RIGHT_VALUE = "a2";
    private static final Long DEFAULT_MODEL_ID = 1000L;

    @Mock
    private DiffRepository mockDiffRepository;

    @Mock
    private ApplicationEventPublisher mockPublisher;

    @Captor
    private ArgumentCaptor<LeftSideCreatedEvent> leftSideCreatedEventArgumentCaptor;
    @Captor
    private ArgumentCaptor<RightSideCreatedEvent> rightSideCreatedEventArgumentCaptor;
    @Captor
    private ArgumentCaptor<ComparisionModel> comparisionResultArgumentCaptor;

    @InjectMocks
    private DiffService unit;

    @Before
    public void setUp() {
        unit.setApplicationEventPublisher(mockPublisher);
    }

    @Test
    public void findById() {
        //GIVEN
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .result(ComparisionResult.builder()
                                                                                        .status(EQUAL)
                                                                                        .build())
                                                               .build();
        when(mockDiffRepository.getById(anyLong())).thenReturn(expectedModel);

        //WHEN
        final ComparisionModel actual = unit.findById(DEFAULT_MODEL_ID);

        //THEN
        assertEquals(expectedModel, actual);
        verify(mockDiffRepository, times(1)).getById(DEFAULT_MODEL_ID);
        verifyNoMoreInteractions(mockDiffRepository);
        verifyZeroInteractions(mockPublisher);
    }

    @Test
    public void findResultById() {
        //GIVEN
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(EQUAL)
                                                                  .build();
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .result(expectedResult)
                                                               .build();
        when(mockDiffRepository.getById(anyLong())).thenReturn(expectedModel);

        //WHEN
        final ComparisionResult actual = unit.findResultById(DEFAULT_MODEL_ID);

        //THEN
        assertEquals(expectedResult, actual);
        verify(mockDiffRepository, times(1)).getById(DEFAULT_MODEL_ID);
        verifyNoMoreInteractions(mockDiffRepository);
        verifyZeroInteractions(mockPublisher);
    }

    @Test
    public void saveLeft() {
        //GIVEN
        final Left left = Left.builder()
                              .id(DEFAULT_MODEL_ID)
                              .value(TEST_LEFT_VALUE)
                              .build();
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .left(left)
                                                               .build();
        final LeftSideCreatedEvent expectedEvent = LeftSideCreatedEvent.builder()
                                                                       .source(unit)
                                                                       .id(DEFAULT_MODEL_ID)
                                                                       .payload(left)
                                                                       .build();

        when(mockDiffRepository.saveOrUpdate(anyLong(), any(ComparisionModel.class)))
                .thenReturn(expectedModel);

        //WHEN
        unit.saveLeft(DEFAULT_MODEL_ID, TEST_LEFT_VALUE);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(leftSideCreatedEventArgumentCaptor.capture());
        verify(mockDiffRepository, times(1))
                .saveOrUpdate(DEFAULT_MODEL_ID, expectedModel);

        final LeftSideCreatedEvent capturedEvent = leftSideCreatedEventArgumentCaptor.getValue();
        assertEquals(expectedEvent.getId(), capturedEvent.getId());
        assertEquals(expectedEvent.getPayload(), capturedEvent.getPayload());
        assertEquals(expectedEvent.getSource(), capturedEvent.getSource());
        assertEquals(expectedEvent.getEventType(), capturedEvent.getEventType());

        verifyNoMoreInteractions(mockPublisher, mockDiffRepository);
    }

    @Test
    public void saveRight() {
        //GIVEN
        final Right right = Right.builder()
                                 .id(DEFAULT_MODEL_ID)
                                 .value(TEST_RIGHT_VALUE)
                                 .build();
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .right(right)
                                                               .build();
        final RightSideCreatedEvent expectedEvent = RightSideCreatedEvent.builder()
                                                                         .source(unit)
                                                                         .id(DEFAULT_MODEL_ID)
                                                                         .payload(right)
                                                                         .build();

        when(mockDiffRepository.saveOrUpdate(anyLong(), any(ComparisionModel.class)))
                .thenReturn(expectedModel);

        //WHEN
        unit.saveRight(DEFAULT_MODEL_ID, TEST_RIGHT_VALUE);

        //THEN
        verify(mockPublisher, times(1))
                .publishEvent(rightSideCreatedEventArgumentCaptor.capture());
        verify(mockDiffRepository, times(1))
                .saveOrUpdate(DEFAULT_MODEL_ID, expectedModel);

        final RightSideCreatedEvent capturedEvent = rightSideCreatedEventArgumentCaptor.getValue();
        assertEquals(expectedEvent.getId(), capturedEvent.getId());
        assertEquals(expectedEvent.getPayload(), capturedEvent.getPayload());
        assertEquals(expectedEvent.getSource(), capturedEvent.getSource());
        assertEquals(expectedEvent.getEventType(), capturedEvent.getEventType());

        verifyNoMoreInteractions(mockPublisher, mockDiffRepository);
    }

    @Test
    public void saveResult() {
        //GIVEN
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(EQUAL)
                                                                  .build();
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .result(expectedResult)
                                                               .build();
        when(mockDiffRepository.getById(anyLong())).thenReturn(expectedModel);
        when(mockDiffRepository.saveOrUpdate(anyLong(), any(ComparisionModel.class)))
                .thenReturn(expectedModel);

        //WHEN
        unit.saveResult(DEFAULT_MODEL_ID, expectedResult);

        //THEN
        verify(mockDiffRepository, times(1))
                .saveOrUpdate(eq(DEFAULT_MODEL_ID), comparisionResultArgumentCaptor.capture());
        assertEquals(expectedModel, comparisionResultArgumentCaptor.getValue());
        verify(mockDiffRepository, times(1)).getById(DEFAULT_MODEL_ID);
        verify(mockDiffRepository, times(1))
                .saveOrUpdate(DEFAULT_MODEL_ID, expectedModel);
        verifyNoMoreInteractions(mockDiffRepository);
        verifyZeroInteractions(mockPublisher);
    }
}