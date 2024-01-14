package com.github.monaboiste.transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
class EmployeeHiredEventListener implements DomainEventListener<EmployeeHired> {
    // e.g. send mail, notify external system to grant permission etc...
    // such scenarios should be handled with e.g. transactional outbox or event sourcing
    // https://dzone.com/articles/transaction-synchronization-and-spring-application

    @Override
    // @EventListener(condition = "'EmployeeHired'.equals(#event.name())")
    // If we use @EventListener, the listener is called synchronously within bounds of the same transaction.
    // But we want the employee to be already created, before the listener is invoked. We wouldn't like the transaction
    // to rollback if there's mail delivery error for instance.
    //
    // @TransactionalEventListener has ability to collaborate with surrounding transaction's phase - we register
    // the callbacks to be invoked after the transaction is being completed (transaction synchronization).
    // By default, the callback is invoked after commit (AFTER_COMMIT setting). If there's no transation running, the
    // method won't be executed.
    @TransactionalEventListener(condition = "'EmployeeHired'.equals(#event.name())")
    public void process(EmployeeHired event) {
        log.info("Received event [{}]", event);
        throw new RuntimeException("Not processed");
    }
}
