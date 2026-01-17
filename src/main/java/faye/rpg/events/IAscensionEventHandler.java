package faye.rpg.events;

import java.util.function.Consumer;

public interface IAscensionEventHandler<T> extends Consumer<T> {
    Class<T> eventType();

    void execute(T event);

    @Override
    default void accept(T event) {
        execute(event);
    }
}