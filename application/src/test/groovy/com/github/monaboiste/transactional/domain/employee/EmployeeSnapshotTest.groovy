package com.github.monaboiste.transactional.domain.employee

import com.github.monaboiste.transactional.util.RandomObject
import org.assertj.core.api.Assertions
import spock.lang.Specification

import java.util.function.BiPredicate

/**
 * Control test to verify, whenever we're including all necessary
 * data in the snapshot.
 * <p>
 * The test does not check the mapping itself - the sole purpose is to make
 * the developer aware of what we publish in the events.
 */
class EmployeeSnapshotTest extends Specification {

    def "should include all relevant fields in snapshot"() {
        given:
        def employee = RandomObject.generate(Employee.class)
        def ignoringFields = new String[]{"pendingEvents"};

        when:
        def snapshot = new EmployeeSnapshot(employee)

        then:
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
            return lhs.toString() == rhs;
        }
    }
}
