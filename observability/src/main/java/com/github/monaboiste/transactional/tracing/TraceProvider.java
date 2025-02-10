package com.github.monaboiste.transactional.tracing;

import java.io.Closeable;

public interface TraceProvider {

    String get();
    Closeable trySet(String traceId);
}
