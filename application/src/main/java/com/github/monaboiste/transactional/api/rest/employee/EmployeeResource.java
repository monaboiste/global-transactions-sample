package com.github.monaboiste.transactional.api.rest.employee;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
class EmployeeResource {

    @JsonView({Read.class})
    private UUID employeeId;

    @JsonView({Read.class, Write.class})
    private String firstName;

    @JsonView({Read.class, Write.class})
    private String lastName;


    interface Read {
    }

    interface Write {
    }
}
