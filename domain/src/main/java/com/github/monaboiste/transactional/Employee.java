package com.github.monaboiste.transactional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity // note: leaving it for now,  also: accessors probably don't have to be public
public class Employee {
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
