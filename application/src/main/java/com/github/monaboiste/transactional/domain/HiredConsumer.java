package com.github.monaboiste.transactional.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HiredConsumer implements EventListener<Hired> {

    private final EmployeeWriteRepository employeeWriteRepository;

    @Override
    @Transactional
    public void process(final Hired event) {
      log.info("Received event: %s".formatted(event));
      employeeWriteRepository.save(event.employee());
    }
}
