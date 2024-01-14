package com.github.monaboiste.transactional;

import java.util.UUID;

public interface EmployeeReadRepository {
    Employee getById(UUID id);
}
