package com.github.monaboiste.transactional.event.bus.spring;

import com.github.monaboiste.transactional.event.BatchEventHandler;
import com.github.monaboiste.transactional.event.DomainEventStore;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class EventBusConfig {

    @Bean
    public BatchEventHandler batchEventSplitter(ApplicationEventPublisher applicationEventPublisher) {
        return new BatchDomainEventSplitter(applicationEventPublisher);
    }

    @Bean
    public BatchEventHandler batchEventStoreHandler(DomainEventStore domainEventStore) {
        return new DomainEventStoreHandler(domainEventStore);
    }
}
