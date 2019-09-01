package com.waes.diff.application.v1;

import com.waes.diff.application.v1.controller.DiffController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SmokeSuitTestIT {

    @Autowired
    private DiffController diffController;

    @Test
    public void contextLoads() {
        assertNotNull(diffController);
    }
}
