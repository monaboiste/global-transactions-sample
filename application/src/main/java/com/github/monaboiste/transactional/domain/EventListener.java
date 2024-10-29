package com.github.monaboiste.transactional.domain;

public interface EventListener<E extends Event> {

    void process(E event);
}
