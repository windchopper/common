package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;

import java.io.File;

public class FileType extends PreferencesEntryFlatType<File> {

    public FileType() {
        super(File::new, File::getAbsolutePath);
    }

}
