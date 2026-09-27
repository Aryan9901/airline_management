package com.aryan.util;

import java.util.function.Consumer;

/**
 * Utility methods commonly used during entity mapping operations.
 */
public final class MapperUtils {

    /**
     * Prevents instantiation of this utility class.
     */
    private MapperUtils() {
        // Prevent instantiation
    }

    /**
     * Applies the provided setter only when the given value is not null.
     *
     * Useful for partial entity updates where null request fields
     * should not overwrite existing entity values.
     *
     * @param value  value to check
     * @param setter setter operation to apply when the value is not null
     * @param <T>    type of the value
     */
    public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}