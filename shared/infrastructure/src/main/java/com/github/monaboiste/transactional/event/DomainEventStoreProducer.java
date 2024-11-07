package com.github.monaboiste.transactional.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Save all events into database in the same transaction.
 */
@Slf4j
@RequiredArgsConstructor
@Component
class DomainEventStoreProducer implements BatchEventHandler, EventHandler<DomainEvent<?>> {

    private final DomainEventStore domainEventStore;

    @Override
    @TransactionalEventListener(
            classes = BatchDomainEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void process(final BatchDomainEvent<?> events) {
        log.info("Processing events {}", events);
        domainEventStore.saveAll(events.domainEvents());
    }

    @Override
    @TransactionalEventListener(
            classes = DomainEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void process(final DomainEvent<?> event) {
        log.info("Processing event {}", event);
        domainEventStore.save(event);
    }
}
