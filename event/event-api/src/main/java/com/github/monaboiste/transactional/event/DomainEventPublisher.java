package com.github.monaboiste.transactional.event;

public interface DomainEventPublisher<T extends Snapshot> {

    void publish(BatchDomainEvent<T> events);
}
