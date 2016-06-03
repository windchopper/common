package ru.wind.common.fx.builder;

import javafx.scene.control.ProgressBar;

import java.util.function.Supplier;

public class ProgressBarBuilder extends ProgressIndicatorBuilder<ProgressBar, ProgressBarBuilder> {

    public ProgressBarBuilder(Supplier<ProgressBar> targetSupplier) {
        super(targetSupplier);
    }

}
