package com.github.monaboiste.transactional.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .build();
    }
}
