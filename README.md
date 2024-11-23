### Sources

- https://pillopl.github.io/reliable-domain-events/
- https://dzone.com/articles/transaction-synchronization-and-spring-application

## Notes

### Publish event from the aggregate

- must create the domain factories for each aggregate
- complicated transaction handling
- ???
- event is being published before commit

### Publish event from the application service

- easier transaction handling
- ???
- events should belong to the domain statement broken

### Return the created event from the aggregate and have an Event Dispatcher

- ???

### Event sourcing

- ???

## Project

The project uses pre-compiled convention plugins. Sometimes Gradle does not detect the changes
in the plugin. When you make any modifications to the gradle scripts, make sure to run `./gradlew clean`

### Conventions

- for strings in gradle scripts use double quotes (`"`) instead of single quotes (`'`)
- use YAML format when defining application properties
- use fluent naming convention for the getters if possible (see: `lombok.config`) to align with
  java `record` (allows seamlessly converting between plain immutable java classes and records)
- prefix spring application libraries' properties file with `application-` - allows to support
  project properties expansion

### Notable tasks
tbc

### TODOs
- change employee/application name module - this most likely will cause naming conflicts in the future