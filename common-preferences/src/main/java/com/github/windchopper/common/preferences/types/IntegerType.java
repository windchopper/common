package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;

public class IntegerType extends PreferencesEntryFlatType<Integer> {

    public IntegerType() {
        super(Integer::parseInt, Objects::toString);
    }

}
