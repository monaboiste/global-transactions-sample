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
        traceProvider.peekLastRegistered().traceId() == "trace-001"
    }

    static class RetainingTraceProvider implements TraceProvider {
        private final TraceProvider delegate
        private final ThreadLocal<Deque<Trace>> memory = ThreadLocal.withInitial(ArrayDeque::new) as ThreadLocal<Deque<Trace>>

        RetainingTraceProvider(final TraceProvider delegate) {
            this.delegate = delegate
        }

        Trace peekLastRegistered() {
            return memory.get().peek()
        }

        @Override
        Trace get() {
            return delegate.get()
        }

        @Override
        Closeable trySet(Trace trace) {
            Closeable closeable = delegate.trySet(trace)
            this.memory.get().push(delegate.get())
            return closeable
        }
    }
}
