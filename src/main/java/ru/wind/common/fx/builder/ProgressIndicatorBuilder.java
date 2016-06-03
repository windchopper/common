package ru.wind.common.fx.builder;

import javafx.scene.control.ProgressIndicator;

import java.util.function.Supplier;

public class ProgressIndicatorBuilder<T extends ProgressIndicator, B extends ProgressIndicatorBuilder<T, B>> extends AbstractControlBuilder<T, B> {

    public ProgressIndicatorBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

}
