package com.waes.diff.application.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.service.DiffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.waes.diff.application.v1.enums.EqualityStatus.DATA_DIFFERENT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTestIT {

    private static final String GET_RESULT_URL = "/v1/diff/{id}/";
    private static final String LEFT_URL = "/v1/diff/{id}/left";
    private static final String RIGHT_URL = "/v1/diff/{id}/right";
    private static final String LEFT_VALUE = "a1";
    private static final String RIGHT_VALUE = "aa2";
    private static final long DEFAULT_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DiffService spyDiffService;

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T readValue(final String value, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(value, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldSaveLeft_thenSaveRight_thenGetResult() throws Exception {

        //WHEN
        final String responseLeft = mockMvc.perform(
                post(LEFT_URL, DEFAULT_ID)
                        .content(LEFT_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                                           .andDo(print())
                                           .andExpect(status().isOk())
                                           .andReturn()
                                           .getResponse()
                                           .getContentAsString();
        //THEN
        assertNotNull(responseLeft);
        assertTrue(responseLeft.contains("Successfully Saved!"));
        verify(spyDiffService, times(1)).saveLeft(DEFAULT_ID, LEFT_VALUE);

        //WHEN
        final String responseRight = mockMvc.perform(
                post(RIGHT_URL, DEFAULT_ID)
                        .content(RIGHT_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                                            .andDo(print())
                                            .andExpect(status().isOk())
                                            .andReturn()
                                            .getResponse()
                                            .getContentAsString();

        //THEN
        assertNotNull(responseRight);
        assertTrue(responseRight.contains("Successfully Saved!"));
        verify(spyDiffService, times(1)).saveRight(DEFAULT_ID, RIGHT_VALUE);

        final String result = mockMvc.perform(get(GET_RESULT_URL, DEFAULT_ID)
                                                      .accept(MediaType.APPLICATION_JSON))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        //THEN
        assertNotNull(result);
        assertEquals(DATA_DIFFERENT, readValue(result, ComparisionResult.class).getStatus());
        verify(spyDiffService, times(1)).findResultById(DEFAULT_ID);
    }

    @Test
    public void shouldSaveLeftValue() throws Exception {

        //WHEN
        final String response = mockMvc.perform(
                post(LEFT_URL, DEFAULT_ID)
                        .content(LEFT_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                                       .andDo(print())
                                       .andExpect(status().isOk())
                                       .andReturn()
                                       .getResponse()
                                       .getContentAsString();

        //THEN
        assertNotNull(response);
        assertTrue(response.contains("Successfully Saved!"));
        verify(spyDiffService, times(1))
                .saveLeft(DEFAULT_ID, LEFT_VALUE);
    }

    @Test
    public void shouldSaveRightValue() throws Exception {
        //WHEN
        final String response = mockMvc.perform(
                post(RIGHT_URL, DEFAULT_ID)
                        .content(RIGHT_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                                       .andDo(print())
                                       .andExpect(status().isOk())
                                       .andReturn()
                                       .getResponse()
                                       .getContentAsString();

        //THEN
        assertNotNull(response);
        assertTrue(response.contains("Successfully Saved!"));
        verify(spyDiffService, times(1))
                .saveRight(DEFAULT_ID, RIGHT_VALUE);
    }
}
