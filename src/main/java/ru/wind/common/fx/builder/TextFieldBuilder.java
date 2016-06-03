package ru.wind.common.fx.builder;

import javafx.scene.control.TextField;

import java.util.function.Supplier;

public class TextFieldBuilder extends AbstractTextInputControlBuilder<TextField, TextFieldBuilder> {

    public TextFieldBuilder(Supplier<TextField> targetSupplier) {
        super(targetSupplier);
    }

}
