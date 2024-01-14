package com.github.monaboiste.transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface EmployeeRepositoryJpa extends
        EmployeeRepository,
        JpaRepository<Employee, Long> {

    @Override
    default Employee getById(UUID id) {
        return getEmployeeByDomainId(id);
    }

    Employee getEmployeeByDomainId(UUID domainId); // note: don't care about fetching by uuid performance here
}
