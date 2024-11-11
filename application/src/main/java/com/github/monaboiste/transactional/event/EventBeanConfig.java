package com.github.monaboiste.transactional.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
