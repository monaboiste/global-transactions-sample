package com.github.monaboiste.transactional.domain.event;

import java.util.List;

public interface DomainEventStore {

    void save(DomainEvent event);

    void saveAll(List<DomainEvent> events);
}
