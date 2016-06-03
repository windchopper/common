package ru.wind.common.fx.builder;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.function.Supplier;

public class FileChooserBuilder extends AbstractBuilder<FileChooser, FileChooserBuilder> {

    public FileChooserBuilder(Supplier<FileChooser> targetSupplier) {
        super(targetSupplier);
    }

    public FileChooserBuilder initialDirectory(File initialDirectory) {
        target.setInitialDirectory(initialDirectory);
        return this;
    }

    public FileChooserBuilder extensionFilters(FileChooser.ExtensionFilter... extensionFilters) {
        target.getExtensionFilters().addAll(extensionFilters);
        return this;
    }

}
