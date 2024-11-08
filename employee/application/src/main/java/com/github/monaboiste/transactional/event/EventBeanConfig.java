package com.github.monaboiste.transactional.event;

import com.github.monaboiste.transactional.employee.event.EmployeeSnapshot;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration(proxyBeanMethods = false)
class EventBeanConfig {

    @Bean
    DomainEventPublisher<EmployeeSnapshot> domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new ApplicationDomainEventPublisher<>(applicationEventPublisher);
    }

    @Bean
    BatchEventHandler batchEventSplitter(ApplicationEventPublisher applicationEventPublisher) {
        return new BatchDomainEventSplitter(applicationEventPublisher);
    }

    @Bean
    BatchEventHandler batchEventStoreHandler(DomainEventStore domainEventStore) {
        return new DomainEventStoreHandler(domainEventStore);
    }

    @Bean
    DomainEventStore domainEventStore(NamedParameterJdbcTemplate jdbc, EventSerializer eventSerializer) {
        return new DomainEventH2JdbcDao(jdbc, eventSerializer);
    }

    @Bean
    EventSerializer eventSerializer() {
        return new JavaEventSerializer();
    }
}
