package com.github.monaboiste.transactional.domain.event;

public interface EventSerializer {

    byte[] serialize(Event event);

    Event deserialize(byte[] bytes);
}

