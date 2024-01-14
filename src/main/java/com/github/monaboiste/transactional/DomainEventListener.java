package com.github.monaboiste.transactional;

public interface DomainEventListener<E extends DomainEvent> {

    void process(E event);
}
