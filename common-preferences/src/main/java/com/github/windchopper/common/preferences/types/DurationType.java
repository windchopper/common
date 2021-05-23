package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.time.Duration;

public class DurationType extends PreferencesEntryFlatType<Duration> {

    public DurationType() {
        super(Duration::parse, Duration::toString);
    }

}
