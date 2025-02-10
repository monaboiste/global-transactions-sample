package com.github.monaboiste.transactional.tracing

import org.aspectj.lang.Aspects
import spock.lang.Shared
import spock.lang.Specification

class TracedAspectTest extends Specification {

    static class UnderTest {
        @Traced(parameter = "traceId")
        void tracedMethod(String traceId) {
            // This method should be intercepted by the aspect
        }
    }

    @Shared
    def instance = new UnderTest()

    @Shared
    def traceProvider = new RetainingTraceProvider(new TraceMdcProvider())

    void setupSpec() {
        def aspect = Aspects.aspectOf(TracedAspect)
        aspect.setTraceProvider(traceProvider)
    }

    def "intercepts annotated method"() {
        given: "registered aspect"

        when: "the traced method is invoked"
        instance.tracedMethod("trace-001")

        then: "last registered trace is set to method parameter"
        traceProvider.peekLastRegistered() == "trace-001"
    }

    static class RetainingTraceProvider implements TraceProvider {
        private final TraceProvider delegate
        private final ThreadLocal<Deque<String>> memory = ThreadLocal.withInitial(ArrayDeque::new) as ThreadLocal<Deque<String>>

        RetainingTraceProvider(final TraceProvider delegate) {
            this.delegate = delegate
        }

        String peekLastRegistered() {
            return memory.get().peek()
        }

        @Override
        String get() {
            return delegate.get()
        }

        @Override
        Closeable trySet(String traceId) {
            Closeable closeable = delegate.trySet(traceId)
            this.memory.get().push(delegate.get())
            return closeable
        }
    }
}
