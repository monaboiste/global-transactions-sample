package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class BatchDomainEventDispenser implements BatchEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @TransactionalEventListener(
            classes = BatchDomainEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void process(BatchDomainEvent events) {
        events.forEach(applicationEventPublisher::publishEvent);
    }
}
