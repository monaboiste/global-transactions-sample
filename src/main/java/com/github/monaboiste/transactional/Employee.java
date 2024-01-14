package com.github.monaboiste.transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long internalId;

    private UUID employeeId;
    private String firstName;
    private String lastName;
}
