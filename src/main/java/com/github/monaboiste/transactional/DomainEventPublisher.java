package com.github.monaboiste.transactional;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
