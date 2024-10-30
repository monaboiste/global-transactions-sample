package com.github.monaboiste.transactional.domain.employee;

import com.github.monaboiste.transactional.domain.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
class SendActivationMailHandler implements EventHandler<Hired> {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            classes = Hired.class,
            fallbackExecution = true
    )
    @Override
    @Async
    public void process(final Hired event) {
      log.info("Sending activation link to employee {}", event.aggregateId());
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
