package com.github.monaboiste.transactional.command;

import java.lang.reflect.ParameterizedType;

public interface CommandHandler<C extends Command> {
    default Class<C> handlingCommandClass() {
        //noinspection unchecked
        return (Class<C>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    void handle(C command);
}
