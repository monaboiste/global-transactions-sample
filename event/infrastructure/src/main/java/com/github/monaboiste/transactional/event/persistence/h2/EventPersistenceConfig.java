package com.github.monaboiste.transactional.event.persistence.h2;

import com.github.monaboiste.transactional.event.DomainEventStore;
import com.github.monaboiste.transactional.event.EventSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration(proxyBeanMethods = false)
public class EventPersistenceConfig {

    @Bean
    public EventSerializer eventSerializer() {
        return new JavaEventSerializer();
    }

    @Bean
    public DomainEventStore domainEventStore(NamedParameterJdbcTemplate jdbc, EventSerializer eventSerializer) {
        return new DomainEventH2JdbcDao(jdbc, eventSerializer);
    }
}
