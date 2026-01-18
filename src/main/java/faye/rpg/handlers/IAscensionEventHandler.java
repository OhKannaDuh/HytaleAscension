/*
 *
 *  * Copyright © 2026 OhKannaDuh, Faye
 *  * Licensed under the GNU AGPL v3.0 or later.
 *  * Source: https://github.com/OhKannaDuh/HytaleAscension
 *
 */

package faye.rpg.handlers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

public interface IAscensionEventHandler<T> extends Consumer<T> {
    default Class<T> eventType() {
        for (Type type : getClass().getGenericInterfaces()) {
            if (type instanceof ParameterizedType pt && pt.getRawType() == IAscensionEventHandler.class) {
                return (Class<T>) pt.getActualTypeArguments()[0];
            }
        }

        throw new IllegalStateException("Cannot determine event type for " + getClass());
    }

    void execute(T event);

    @Override
    default void accept(T event) {
        execute(event);
    }
}