package com.aryan.util;

import java.util.function.Consumer;

public final class MapperUtils {

    private MapperUtils() {
        // Prevent instantiation
    }

    public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}