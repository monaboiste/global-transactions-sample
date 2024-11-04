package com.github.monaboiste.transactional

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringContextTest extends Specification {

    def "context loads"() {
        expect:
        true
    }
}
