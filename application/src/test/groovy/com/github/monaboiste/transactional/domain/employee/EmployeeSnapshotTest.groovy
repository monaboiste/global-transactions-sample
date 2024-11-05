package com.github.monaboiste.transactional.domain.employee

import com.github.monaboiste.transactional.util.Comparators
import com.github.monaboiste.transactional.util.RandomObject
import org.assertj.core.api.Assertions
import spock.lang.Specification

/**
 * Control test to verify, whenever we're including all necessary
 * data in the snapshot.
 * <p>
 * The test does not check the mapping itself - the sole purpose is to make
 * the developer aware of what we publish in the events.
 */
class EmployeeSnapshotTest extends Specification {

    def "should include all relevant fields in snapshot"() {
        given: "an employee object"
        def employee = RandomObject.generate(Employee.class)
        def ignoringFields = new String[]{"pendingEvents"}

        when: "instantiating a snapshot of the object"
        def snapshot = new EmployeeSnapshot(employee)

        then: "all relevant fields should be present in the snapshot"
        // noinspection AssertBetweenInconvertibleTypes
        Assertions
                .assertThat(employee)
                .usingRecursiveComparison()
                .ignoringFields(ignoringFields)
                .withEqualsForFields(Comparators.toStringBasedComparator(), "employeeId", "workEmail")
                .isEqualTo(snapshot)
    }
}
