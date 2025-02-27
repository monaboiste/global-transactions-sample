package com.github.monaboiste.transactional.tracing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Limitations if used with Spring AOP:
 * <ul>
 * <li> works only on Spring-managed beans and intercepts <b>public</b> or <b>package-private</b> methods
 * <li> it cannot intercept static or <i>actual class instance</i> methods, i.e., it does not work for nested
 * invocations (method A calls method B etc...), including constructors or {@code @PostConstruct}
 * </ul>
 * <p>
 * Limitations if used with pure AspectJ:
 * <ul>
 * <li> dependency injection will work only if the context is fully initialized; {@code @PostConstruct}
 * may be invoked before all proxies are initialized, so it's better to avoid using aspects from init methods
 * or constructors
 * </ul>
 *
 * @see TracedAspect
 * @see TraceProvider
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Traced {

    /**
     * Reads the traceId of provided method name parameter into trace identifier.
     */
    String parameter() default "";
}
