package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class IntegerType extends PreferencesEntryFlatType<Integer> {

    public IntegerType() {
        super(
            stringValue -> Optional.ofNullable(stringValue)
                .filter(not(String::isBlank))
                .map(Integer::parseInt)
                .orElse(null),
            integerValue -> Optional.ofNullable(integerValue)
                .map(Objects::toString)
                .orElse(null));
    }

}
