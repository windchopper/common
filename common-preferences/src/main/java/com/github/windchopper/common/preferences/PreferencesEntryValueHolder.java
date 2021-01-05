package com.github.windchopper.common.preferences;

import java.time.Instant;

public class PreferencesEntryValueHolder<T> {

    private Instant timestamp;
    private T value;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null || timestamp == null;
    }

    public boolean isNotEmpty() {
        return value != null && timestamp != null;
    }

}
