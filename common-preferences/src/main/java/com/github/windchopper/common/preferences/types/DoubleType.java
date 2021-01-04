package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class DoubleType extends PreferencesEntryFlatType<Double> {

    public DoubleType() {
        super(
            stringValue -> Optional.ofNullable(stringValue)
                .filter(not(String::isBlank))
                .map(Double::parseDouble)
                .orElse(null),
            doubleValue -> Optional.ofNullable(doubleValue)
                .map(Objects::toString)
                .orElse(null));
    }

}
