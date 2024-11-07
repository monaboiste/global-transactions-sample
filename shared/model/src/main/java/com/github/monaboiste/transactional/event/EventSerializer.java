package com.github.monaboiste.transactional.event;

public interface EventSerializer {

    <T extends Snapshot> byte[] serialize(Event<T> event);

    <T extends Snapshot> Event<T> deserialize(byte[] bytes);
}

