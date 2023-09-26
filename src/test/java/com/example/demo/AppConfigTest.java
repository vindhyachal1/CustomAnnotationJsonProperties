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
        // You can customize the properties for your tests here if needed.
    }

    @Test
    public void testPropertiesLoadedFromJson() {
        assertEquals("MyApp", appConfig.getName());
        assertEquals("1.0", appConfig.getVersion());
    }

    @Test
    public void testDeprecatedPropertyWarning() {
        String description = appConfig.getDescription();

        // Check if description is not null
        assertTrue(description != null);

        // Check if the property is empty or null
        assertTrue(description.isEmpty() || description.isBlank());

        // Check for the deprecated property warning message in the console
        // You can customize this part based on your logging configuration
        // In a real application, you would typically use a logging framework
        // like SLF4J and capture log messages for testing.
        String consoleOutput = captureConsoleOutput(() -> {
            AppConfig deprecatedPropertyAppConfig = new AppConfig();
            deprecatedPropertyAppConfig.getDescription();
        });

        assertTrue(consoleOutput.contains("Warning: Property 'description' is deprecated."));
    }

    // Utility method to capture console output
    private String captureConsoleOutput(Runnable action) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        action.run();

        System.setOut(originalOut);
        return outContent.toString();
    }
}



