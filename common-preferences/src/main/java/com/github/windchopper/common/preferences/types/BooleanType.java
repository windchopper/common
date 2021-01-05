package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;

public class BooleanType extends PreferencesEntryFlatType<Boolean> {

    public BooleanType() {
        super(Boolean::parseBoolean, Objects::toString);
    }

}
