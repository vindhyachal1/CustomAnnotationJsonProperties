package com.example.demo;

import com.example.demo.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AppConfig.class)
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testPropertiesLoadedFromJson() {
        assertEquals("MyApp", appConfig.getName());
        assertEquals("1.0", appConfig.getVersion());
    }

    @Test
    public void testDeprecatedPropertyWarning() {
        String description = appConfig.getDescription();

        assertTrue(description != null);

        assertTrue(description.isEmpty() || description.isBlank());

        String consoleOutput = captureConsoleOutput(() -> {
            AppConfig deprecatedPropertyAppConfig = new AppConfig();
            deprecatedPropertyAppConfig.getDescription();
        });

        assertTrue(consoleOutput.contains("Warning: Property 'description' is deprecated."));
    }

    private String captureConsoleOutput(Runnable action) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        action.run();

        System.setOut(originalOut);
        return outContent.toString();
    }
}



