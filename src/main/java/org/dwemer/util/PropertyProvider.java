package org.dwemer.util;

import java.util.Objects;

public final class PropertyProvider {

    public static final String DISCORD_TOKEN = "attunementSphereToken";

    private static final String PROPERTY_NAME_NOT_NULL = "propertyName must not be null";
    private static final String PROPERTY_NAME_NOT_EMPTY = "propertyName must not be empty";

    private PropertyProvider() {}

    public static String getProperty(String propertyName) {
        checkInputValidity(propertyName);
        String result = getSystemProperty(propertyName);
        if (result != null) {
            return result;
        }
        return getEnvProperty(propertyName);
    }

    public static String getSystemProperty(String propertyName) {
        checkInputValidity(propertyName);
        return System.getProperty(propertyName);
    }

    public static String getEnvProperty(String propertyName) {
        checkInputValidity(propertyName);
        return System.getenv(propertyName);
    }

    private static void checkInputValidity(String propertyName) {
        Objects.requireNonNull(propertyName, PROPERTY_NAME_NOT_NULL);
        if (propertyName.trim().isEmpty()) {
            throw new IllegalArgumentException(PROPERTY_NAME_NOT_EMPTY);
        }
    }
}
