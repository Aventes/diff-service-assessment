package com.waes.diff.application.v1.controller;

import com.waes.diff.application.v1.controller.model.StringResponse;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.service.DiffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.waes.diff.application.v1.enums.EqualityStatus.EQUAL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class DiffControllerTest {

    private static final String TEST_LEFT_VALUE = "a1";
    private static final String TEST_RIGHT_VALUE = "a2";
    private static final Long DEFAULT_MODEL_ID = 1000L;
    public static final String SUCCESS_RESPONSE = "Successfully Saved!";

    @Mock
    private DiffService mockDiffService;

    @InjectMocks
    private DiffController unit;

    @Test
    public void getComparisionResult() {
        //GIVEN
        final ComparisionResult expectedResult = ComparisionResult.builder()
                                                                  .status(EQUAL)
                                                                  .build();
        when(mockDiffService.findResultById(anyLong())).thenReturn(expectedResult);

        //WHEN
        final ComparisionResult actual = unit.getComparisionResult(DEFAULT_MODEL_ID);

        //THEN
        assertEquals(expectedResult, actual);
        verify(mockDiffService, times(1)).findResultById(DEFAULT_MODEL_ID);
        verifyNoMoreInteractions(mockDiffService);
    }

    @Test
    public void saveLeft() {
        //GIVEN
        final StringResponse expectedResponse = StringResponse.builder()
                                                   .response(SUCCESS_RESPONSE)
                                                   .status(OK)
                                                   .build();

        //WHEN
        final StringResponse actual = unit.saveLeft(DEFAULT_MODEL_ID, TEST_LEFT_VALUE);

        //THEN
        assertEquals(expectedResponse, actual);
        verify(mockDiffService, times(1)).saveLeft(DEFAULT_MODEL_ID, TEST_LEFT_VALUE);
        verifyNoMoreInteractions(mockDiffService);
    }

    @Test
    public void saveRight() {
        //GIVEN
        final StringResponse expectedResponse = StringResponse.builder()
                                                              .response(SUCCESS_RESPONSE)
                                                              .status(OK)
                                                              .build();

        //WHEN
        final StringResponse actual = unit.saveRight(DEFAULT_MODEL_ID, TEST_RIGHT_VALUE);

        //THEN
        assertEquals(expectedResponse, actual);
        verify(mockDiffService, times(1)).saveRight(DEFAULT_MODEL_ID, TEST_RIGHT_VALUE);
        verifyNoMoreInteractions(mockDiffService);
    }
}