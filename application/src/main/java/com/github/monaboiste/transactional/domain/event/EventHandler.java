package com.github.monaboiste.transactional.domain.event;

public interface EventHandler<E extends Event<?>> {

    void process(E event);
}

