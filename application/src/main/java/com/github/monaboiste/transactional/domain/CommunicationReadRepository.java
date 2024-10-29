package com.github.monaboiste.transactional.domain;

import java.util.List;
import java.util.UUID;

public interface CommunicationReadRepository {

    List<Communication> findByEmployeeId(UUID employeeId);
}
