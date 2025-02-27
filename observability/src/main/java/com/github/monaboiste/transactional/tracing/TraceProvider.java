package com.github.monaboiste.transactional.tracing;

import java.io.Closeable;

public interface TraceProvider {

    Trace get();
    Closeable trySet(Trace trace);
}
