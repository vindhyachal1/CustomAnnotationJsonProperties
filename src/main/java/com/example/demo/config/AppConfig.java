package com.example.demo.config;

import com.example.demo.DeprecatedProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String name;
    private String version;
    private String description;

    public AppConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("application.json");
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Map<String, String> properties = objectMapper.convertValue(jsonNode, Map.class);
            Field[] fields = this.getClass().getDeclaredFields();

            for (Field field : fields) {
                String fieldName = field.getName();
                if (properties.containsKey(fieldName)) {
                    field.setAccessible(true);
                    field.set(this, properties.get(fieldName));
                    if (field.isAnnotationPresent(DeprecatedProperty.class)) {
                        System.out.println("Warning: Property '" + fieldName + "' is deprecated.");
                    }
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

