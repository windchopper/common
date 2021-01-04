package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class BooleanType extends PreferencesEntryFlatType<Boolean> {

    public BooleanType() {
        super(
            stringValue -> Optional.ofNullable(stringValue)
                .filter(not(String::isBlank))
                .map(Boolean::parseBoolean)
                .orElse(null),
            booleanValue -> Optional.ofNullable(booleanValue)
                .map(Objects::toString)
                .orElse(null));
    }

}
