package com.github.monaboiste.transactional.domain.event;

public interface DomainEventPublisher {

    void publish(BatchDomainEvent events);
}
