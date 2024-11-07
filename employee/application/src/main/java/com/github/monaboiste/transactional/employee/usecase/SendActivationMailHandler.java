package com.github.monaboiste.transactional.employee.usecase;

import com.github.monaboiste.transactional.employee.event.Hired;
import com.github.monaboiste.transactional.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class SendActivationMailHandler implements EventHandler<Hired> {

    @Override
    @Async
    @EventListener(classes = Hired.class)
    public void process(final Hired event) {
      log.info("Sending activation link to employee {}", event.payload().workEmail());
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
