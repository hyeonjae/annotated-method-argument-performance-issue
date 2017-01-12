package com.springapp.mvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();

        // warm up
        for (int i = 0; i < 3000; i++) {
            this.mockMvc.perform(post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Dooray-App-Key", UUID.randomUUID().toString()))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

    @Test
    public void simple() throws Exception {
        StopWatch watch = new StopWatch();

        watch.start();
        for (int i = 0; i < 50000; i++) {
            if (i%5000 == 0) {
                watch.stop();
                System.out.println(watch.getTotalTimeMillis());
                watch.start();
            }
            this.mockMvc.perform(post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Dooray-App-Key", UUID.randomUUID().toString()))
                    .andExpect(status().isOk())
                    .andReturn();
        }
        watch.stop();
        System.out.println(watch.getTotalTimeMillis());
    }
}
