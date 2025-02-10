package com.github.monaboiste.transactional.tracing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.Closeable;

@Aspect
public class TracedAspect {

    private TraceProvider traceProvider;

    @Around("@annotation(tracedAnnotation) && execution(* *(..))")
    public Object around(ProceedingJoinPoint joinPoint, Traced tracedAnnotation) throws Throwable {
        String traceId = extractTraceId(joinPoint, tracedAnnotation);
        try (Closeable ignored = traceProvider.trySet(traceId)) {
            return joinPoint.proceed();
        }
    }

    /**
     * Extracts {@link Traced#parameter()} value into {@code traceId}.
     *
     * @return {@code traceId} value or {@code null}.
     */
    private String extractTraceId(ProceedingJoinPoint joinPoint, Traced tracedAnnotation) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        String targetParameterName = tracedAnnotation.parameter();

        String traceId = null;
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(targetParameterName) && args[i] != null) {
                traceId = args[i].toString();
            }
        }
        return traceId;
    }

    public void setTraceProvider(TraceProvider traceProvider) {
        this.traceProvider = traceProvider;
    }
}
