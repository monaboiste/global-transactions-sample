package com.github.monaboiste.transactional;

public interface EmployeeReadRepository {
    Employee getById(Long id);
}
