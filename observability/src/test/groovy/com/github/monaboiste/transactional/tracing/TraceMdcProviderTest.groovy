package com.github.monaboiste.transactional.tracing


import spock.lang.Specification

class TraceMdcProviderTest extends Specification {

    TraceProvider traceProvider = new TraceMdcProvider()

    def "should register trace id"() {
        given: "a trace id"
        def traceId = "trace-001"

        when: "setting the trace"
        Closeable closeable = traceProvider.trySet(traceId)

        then: "retrieves saved trace"
        traceProvider.get() == "trace-001"

        cleanup:
        closeable.close()
    }

    def "should generate trace id if it's absent"() {
        when: "setting null trace"
        Closeable closeable = traceProvider.trySet(null)

        then: "generates a new trace"
        traceProvider.get() != null

        cleanup:
        closeable.close()
    }

    def "clears state after close"() {
        given: "set trace"
        Closeable closeable = traceProvider.trySet(null)

        when: "trace context is closed"
        closeable.close()

        then: "trace state is cleared"
        traceProvider.get() == null
    }

    def "handles nested invocations"() {
        given: "a trace id"
        def traceId = "trace-001"

        and: "a method which calls other method"
        Runnable inner = () -> {
            try (Closeable closeable = traceProvider.trySet(null)) {
                assert traceProvider.get() == traceId
            }
        }
        Runnable outer = () -> {
            try (Closeable closeable = traceProvider.trySet(traceId)) {
                inner.run()
                assert traceProvider.get() == traceId
            }
        }

        when: "outer method is invoked"
        outer.run()

        then: "sets trace id just once and clears state"
        traceProvider.get() == null
    }
}
