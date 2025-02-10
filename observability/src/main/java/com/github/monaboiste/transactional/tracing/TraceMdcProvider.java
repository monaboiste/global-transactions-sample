package com.github.monaboiste.transactional.tracing;

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
    public String get() {
        return MDC.get(TRACE_ID);
    }

    @Override
    public Closeable trySet(String traceId) {
        String currentTraceId = get();
        if (currentTraceId != null) {
            return new NoOpCloseable();
        }

        boolean generated = false;
        if (traceId == null || traceId.isEmpty()) {
            traceId = traceIdGenerator.generate();
            generated = true;
        }
        if (log.isDebugEnabled() && generated) {
            log.debug("Generated new {} value - {}", TRACE_ID, traceId);
        }

        return MDC.putCloseable(TRACE_ID, traceId);
    }

    private static class NoOpCloseable implements Closeable {

        @Override
        @SuppressWarnings("squid:S1186")
        public void close() {
        }
    }
}
