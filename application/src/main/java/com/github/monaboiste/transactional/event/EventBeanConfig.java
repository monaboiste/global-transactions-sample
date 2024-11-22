package com.github.monaboiste.transactional.event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration(proxyBeanMethods = false)
class EventBeanConfig {

    @Bean
    BatchEventHandler batchEventSplitter(ApplicationEventPublisher applicationEventPublisher) {
        return new BatchDomainEventSplitter(applicationEventPublisher);
    }

    @Bean
    BatchEventHandler batchEventStoreHandler(DomainEventStore domainEventStore) {
        return new DomainEventStoreHandler(domainEventStore);
    }

    @Bean
    EventSerializer eventSerializer() {
        return new JavaEventSerializer();
    }

    @Bean
    DomainEventStore domainEventStore(@Qualifier("eventJdbcTemplate") NamedParameterJdbcTemplate jdbc,
                                      EventSerializer eventSerializer) {
        return new DomainEventH2JdbcDao(jdbc, eventSerializer);
    }
}
