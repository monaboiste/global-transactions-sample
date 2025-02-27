package com.github.monaboiste.transactional.tracing;

/**
 * Trace context.
 *
 * @param traceId parent trace identifier.
 */
public record Trace(String traceId) {

    public Trace(String traceId) {
        this.traceId = (traceId == null) ? "" : traceId;
    }

    boolean isEmpty() {
        return traceId.isEmpty();
    }

    @Override
    public String toString() {
        return traceId;
    }
}
