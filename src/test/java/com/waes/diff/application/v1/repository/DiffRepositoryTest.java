package com.waes.diff.application.v1.repository;

import com.waes.diff.application.v1.model.domain.ComparisionModel;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.waes.diff.application.v1.enums.EqualityStatus.EQUAL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link DiffRepository}
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DiffRepositoryTest {
    private static final Long DEFAULT_MODEL_ID = 1000L;

    @Mock
    private StubDataSource mockStubDataSource;

    @InjectMocks
    private DiffRepository unit;

    @Test
    public void saveOrUpdate() {
        //GIVEN
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .result(ComparisionResult.builder()
                                                                                        .status(EQUAL)
                                                                                        .build())
                                                               .build();
        when(mockStubDataSource.saveOrUpdate(anyLong(), any(ComparisionModel.class))).thenReturn(expectedModel);

        //WHEN
        final ComparisionModel actual = unit.saveOrUpdate(DEFAULT_MODEL_ID, expectedModel);

        //THEN
        assertEquals(expectedModel, actual);
        verify(mockStubDataSource, times(1)).saveOrUpdate(DEFAULT_MODEL_ID, expectedModel);
        verifyNoMoreInteractions(mockStubDataSource);
    }

    @Test
    public void getById() {
        //GIVEN
        final ComparisionModel expectedModel = ComparisionModel.builder()
                                                               .id(DEFAULT_MODEL_ID)
                                                               .result(ComparisionResult.builder()
                                                                                        .status(EQUAL)
                                                                                        .build())
                                                               .build();
        when(mockStubDataSource.getModelById(anyLong())).thenReturn(expectedModel);

        //WHEN
        final ComparisionModel actual = unit.getById(DEFAULT_MODEL_ID);

        //THEN
        assertEquals(expectedModel, actual);
        verify(mockStubDataSource, times(1)).getModelById(DEFAULT_MODEL_ID);
        verifyNoMoreInteractions(mockStubDataSource);
    }
}