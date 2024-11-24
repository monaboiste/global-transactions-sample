# Spring utilities

As for now all utilities reside inside `com.github.monaboiste.transactional` package, so `@ComponentScan`
can pick up all declared beans. If this module grows, we might consider moving all beans to the different
package and load selected ones via `@Import`, so that we can avoid loading unnecessary beans into Spring Context. 