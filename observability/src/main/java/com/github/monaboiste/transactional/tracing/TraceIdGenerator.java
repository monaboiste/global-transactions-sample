package com.github.monaboiste.transactional.tracing;

import org.jetbrains.annotations.NotNull;

/**
 * Implementation should be compatible with {@link TraceProvider}.
 */
public interface TraceIdGenerator {

    @NotNull Trace generate();
}
