package com.github.monaboiste.transactional.tracing;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.Closeable;

class TraceMdcProvider implements TraceProvider {
    private static final Logger log = LoggerFactory.getLogger(TraceMdcProvider.class);
    private static final String TRACE_ID = "requestId";

    private final TraceIdGenerator traceIdGenerator;

    TraceMdcProvider() {
        this(new RandomTraceIdGenerator());
    }


    TraceMdcProvider(TraceIdGenerator traceIdGenerator) {
        this.traceIdGenerator = traceIdGenerator;
    }

    @Override
    public Trace get() {
        return new Trace(MDC.get(TRACE_ID));
    }

    @Override
    public Closeable trySet(@NotNull Trace trace) {
        Trace currentTrace = get();
        if (!currentTrace.isEmpty()) {
            return new NoOpCloseable();
        }

        boolean generated = false;
        if (trace.isEmpty()) {
            trace = traceIdGenerator.generate();
            generated = true;
        }
        if (log.isDebugEnabled() && generated) {
            log.debug("Generated new {} traceId - {}", TRACE_ID, trace);
        }

        return MDC.putCloseable(TRACE_ID, trace.traceId());
    }

    private static class NoOpCloseable implements Closeable {

        @Override
        @SuppressWarnings("squid:S1186")
        public void close() {
        }
    }
}
