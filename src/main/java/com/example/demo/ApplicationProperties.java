package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Configuration
public class ApplicationProperties {

    @Bean
    public Map<String, String> applicationProperties() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("application.json");
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            return objectMapper.convertValue(jsonNode, Map.class);
        }
    }
}

