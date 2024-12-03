package com.github.monaboiste.transactional

import com.github.monaboiste.transactional.util.client.ActuatorClient
import spock.lang.Shared
import spock.lang.Specification

class ApplicationHealthTest extends Specification {

    @Shared
    def host = System.getProperty("host", "http://localhost:8080")

    @Shared
    ActuatorClient http = new ActuatorClient(host)

    def "passes health check"() {
        given:"server is running on ${host}"

        when: "calling health endpoint"
        def response = http.health()

        then: "the status is 'UP'"
        response.statusCode() == 200
        response.body().get("status") == "UP"
    }
}
