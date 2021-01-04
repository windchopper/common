package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PathType extends PreferencesEntryFlatType<Path> {

    public PathType() {
        super(Paths::get, Objects::toString);
    }

}
