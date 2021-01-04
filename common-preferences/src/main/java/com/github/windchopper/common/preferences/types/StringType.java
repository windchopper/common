package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

public class StringType extends PreferencesEntryFlatType<String> {

    public StringType() {
        super(
            stringValue -> stringValue,
            stringValue -> stringValue);
    }

}
