package com.github.monaboiste.transactional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Setter(AccessLevel.MODULE)
@Getter(AccessLevel.MODULE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
class Employee {
    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "domain_id")
    private UUID domainId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "token")
    private String token;

    public void activateWith(String token) {
        this.token = token;
    }

    public boolean hasToken() {
        return !(token == null || token.isEmpty());
    }
}
