package com.github.monaboiste.transactional.domain;

import java.util.List;

public interface EventPublisher {

    default void publish(List<Event> events) {
        events.forEach(this::publish);
    }

    void publish(Event event);
}
