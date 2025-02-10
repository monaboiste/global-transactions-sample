package com.github.monaboiste.transactional;

import com.github.monaboiste.transactional.tracing.TraceProvider;
import com.github.monaboiste.transactional.tracing.TracedAspect;
import org.aspectj.lang.Aspects;

public class ObservabilityFactory {

    public TracedAspect tracedAspect(TraceProvider traceProvider) {
        TracedAspect tracedAspect = Aspects.aspectOf(TracedAspect.class);
        tracedAspect.setTraceProvider(traceProvider);
        return tracedAspect;
    }
}
