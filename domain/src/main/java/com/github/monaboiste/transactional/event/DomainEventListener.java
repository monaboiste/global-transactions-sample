package com.github.monaboiste.transactional.event;

public interface DomainEventListener<E extends DomainEvent> {

    void process(E event);
}
