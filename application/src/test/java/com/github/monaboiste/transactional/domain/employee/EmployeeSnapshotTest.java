package com.github.monaboiste.transactional.domain.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.BiPredicate;

/**
 * Control test to verify, whenever we're including all necessary
 * data in the snapshot.
 * <p>
 * The test does not check the mapping itself - the sole purpose is to make
 * the developer aware of what we publish in the events.
 */
class EmployeeSnapshotTest {

    @Test
    void includes_all_relevant_fields_in_snapshot() {
        Employee employee = new Employee(
                new EmployeeId(UUID.nameUUIDFromBytes(new byte[]{0})),
                "Joe",
                "Doe",
                new Email("xx@xx.com"),
                false,
                "0"
        );

        var ignoringFields = new String[]{"pendingEvents"};

        EmployeeSnapshot snapshot = new EmployeeSnapshot(employee);

        // noinspection AssertBetweenInconvertibleTypes
        Assertions
                .assertThat(employee)
                .usingRecursiveComparison()
                .ignoringFields(ignoringFields)
                .withEqualsForFields(toStringBasedComparator(), "employeeId", "workEmail")
                .isEqualTo(snapshot);
    }


    private static <T> BiPredicate<T, String> toStringBasedComparator() {
        return (lhs, rhs) -> {
            if (lhs == null && rhs == null) {
                return true;
            }
            if (lhs == null || rhs == null) {
                return false;
            }
            return lhs.toString().equals(rhs);
        };
    }
}