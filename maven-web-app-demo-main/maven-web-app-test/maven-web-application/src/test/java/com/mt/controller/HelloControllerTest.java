package com.mt.controller;

import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;   // <-- key change

public class HelloControllerTest {

    @Test
    public void testHome() {
        // ExtendedModelMap implements Model
        Model model = new ExtendedModelMap();

        HelloController controller = new HelloController();
        String view = controller.home(model);

        assertEquals("home", view);
        assertTrue(model.asMap().containsKey("message"));
        assertEquals("Hello, DevOps Academy!", model.asMap().get("message"));
    }
}


