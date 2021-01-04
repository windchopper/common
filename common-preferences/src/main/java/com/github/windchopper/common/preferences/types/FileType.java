package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.io.File;
import java.util.Optional;

import static java.util.function.Predicate.not;

public class FileType extends PreferencesEntryFlatType<File> {

    public FileType() {
        super(
            stringValue -> Optional.ofNullable(stringValue)
                .filter(not(String::isBlank))
                .map(File::new)
                .orElse(null),
            fileValue -> Optional.ofNullable(fileValue)
                .map(File::getAbsolutePath)
                .orElse(null));
    }

}
