package com.github.monaboiste.transactional.employee.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class RestConfig {

    @Bean
    public ObjectMapper restSerializer() {
        return JsonMapper.builder()
                .build();
    }
}
