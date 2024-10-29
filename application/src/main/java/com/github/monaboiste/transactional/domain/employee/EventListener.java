package com.github.monaboiste.transactional.domain.employee;

public interface EventListener<E extends Event> {

    void process(E event);
}
