package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import static com.github.windchopper.common.util.stream.FallibleFunction.identity;

public class StringType extends PreferencesEntryFlatType<String> {

    public StringType() {
        super(identity(), identity());
    }

}
