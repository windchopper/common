package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;

public class DoubleType extends PreferencesEntryFlatType<Double> {

    public DoubleType() {
        super(Double::parseDouble, Objects::toString);
    }

}
