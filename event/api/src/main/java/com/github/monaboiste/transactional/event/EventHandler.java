package com.github.monaboiste.transactional.event;

public interface EventHandler<E extends Event<?>> {

    void process(E event);
}

