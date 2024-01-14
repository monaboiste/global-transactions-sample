package com.github.monaboiste.transactional.event;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
