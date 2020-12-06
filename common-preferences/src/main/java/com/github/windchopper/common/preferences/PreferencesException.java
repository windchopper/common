package com.github.windchopper.common.preferences;

public class PreferencesException extends RuntimeException {

    public PreferencesException(String message) {
        super(message);
    }

    public PreferencesException(String message, Throwable cause) {
        super(message, cause);
    }

}
