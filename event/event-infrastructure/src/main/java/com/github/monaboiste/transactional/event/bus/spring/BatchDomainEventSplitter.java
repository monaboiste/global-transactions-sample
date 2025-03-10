package com.github.monaboiste.transactional.event.bus.spring;

import com.github.monaboiste.transactional.event.BatchDomainEvent;
import com.github.monaboiste.transactional.event.BatchEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
class BatchDomainEventSplitter implements BatchEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @TransactionalEventListener(
            classes = BatchDomainEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void process(BatchDomainEvent<?> events) {
        log.info("Splitting events...");
        events.forEach(applicationEventPublisher::publishEvent);
    }
}
