package com.github.monaboiste.transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class EmployeeRepositoryInMemory implements EmployeeRepository {
    private final Map<UUID, Employee> employees;
    private final AtomicLong idGenerator;

    EmployeeRepositoryInMemory() {
        this(new ConcurrentHashMap<>(), new AtomicLong(0L));
    }

    EmployeeRepositoryInMemory(Map<UUID, Employee> employees, AtomicLong idGenerator) {
        this.employees = employees;
        this.idGenerator = idGenerator;
    }


    @Override
    public Employee getById(UUID id) {
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new RuntimeException("Employee " + id + ": not found");
        }
        return employee;
    }

    @Override
    public Employee save(Employee entity) {
        long id = idGenerator.incrementAndGet();
        entity.setEmployeeId(id);
        employees.put(entity.getDomainId(), entity);
        return entity;
    }
}
