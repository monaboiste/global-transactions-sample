package com.github.monaboiste.transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
class EmployeeHiredEventListener implements DomainEventListener {
    // e.g. send mail, notify external system to grant permission etc...

    @Override
    @TransactionalEventListener(condition = "'EmployeeHired'.equals(#event.name())")
    public void process(DomainEvent event) {
        log.info("Received event [{}]", event);
        throw new RuntimeException("Not processed");
    }
}
