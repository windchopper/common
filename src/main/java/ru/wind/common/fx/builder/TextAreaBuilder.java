package ru.wind.common.fx.builder;

import javafx.scene.control.TextArea;

import java.util.function.Supplier;

public class TextAreaBuilder extends AbstractTextInputControlBuilder<TextArea, TextAreaBuilder> {

    public TextAreaBuilder(Supplier<TextArea> targetSupplier) {
        super(targetSupplier);
    }

}
