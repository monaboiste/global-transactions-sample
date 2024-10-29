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