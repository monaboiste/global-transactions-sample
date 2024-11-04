package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
class BatchDomainEventDispenser implements BatchEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @TransactionalEventListener(
            classes = BatchDomainEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void process(BatchDomainEvent<?> events) {
        log.info("Dispense events...");
        events.forEach(applicationEventPublisher::publishEvent);
    }
}
