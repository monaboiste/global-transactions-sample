package com.github.monaboiste.transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class CommandDispatcher {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> commandHandlers;

    public CommandDispatcher(List<CommandHandler<? extends Command>> commandHandlers) {
        if (commandHandlers.isEmpty()) {
            log.warn("No command handlers registered");
            this.commandHandlers = new HashMap<>();
            return;
        }
        this.commandHandlers = commandHandlers.stream()
                .collect(toMap(CommandHandler::handlingCommandClass, identity()));
    }

    public <C extends Command, O> CommandHandler<C> resolve(Class<C> clazz) {
        @SuppressWarnings("unchecked")
        var commandHandler = ((CommandHandler<C>) commandHandlers.get(clazz));
        if (commandHandler == null) {
            throw new RuntimeException("Cannot dispatch command: " + clazz.getSimpleName()
                    + ": no command handler found");
        }
        return commandHandler;
    }
}
