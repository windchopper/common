package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class PathType extends PreferencesEntryFlatType<Path> {

    public PathType() {
        super(
            stringValue -> Optional.ofNullable(stringValue)
                .filter(not(String::isBlank))
                .map(Paths::get)
                .orElse(null),
            pathValue -> Optional.ofNullable(pathValue)
                .map(Objects::toString)
                .orElse(null));
    }

}
