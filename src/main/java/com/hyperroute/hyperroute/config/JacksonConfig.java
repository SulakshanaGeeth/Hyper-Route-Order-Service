package com.hyperroute.hyperroute.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    /**
     * Registers Jackson's JavaTimeModule to enable serialization and
     * deserialization of Java 8+ date/time types such as Instant,
     * LocalDate, LocalDateTime, and ZonedDateTime.
     * Required because Kafka JsonSerializer uses Jackson internally and
     * cannot handle java.time types by default, resulting in errors like:
     * "Java 8 date/time type java.time.Instant not supported by default".
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
