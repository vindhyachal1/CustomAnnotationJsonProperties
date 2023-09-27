package com.example.demo.config;

import com.example.demo.property.DeprecatedProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class AppConfig {

    @Value("${app.name}")
    private String name;

    @Value("${app.version}")
    private String version;

    @DeprecatedProperty(message = "This property is deprecated. Use a different property instead.")
    @Value("${app.test}")
    private String test; // Marked as deprecated

    @Autowired
    public AppConfig() {
        // Initialize properties from configuration
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void checkForDeprecatedProperties() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (field.isAnnotationPresent(DeprecatedProperty.class)) {
                System.out.println("Warning: Property '" + fieldName + "' is deprecated.");
            }
        }
    }
}
