package com.github.monaboiste.transactional.domain.event;

public interface DomainEventPublisher<T extends Snapshot> {

    void publish(BatchDomainEvent<T> events);
}
