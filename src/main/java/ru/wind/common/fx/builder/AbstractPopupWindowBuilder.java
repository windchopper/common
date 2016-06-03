package ru.wind.common.fx.builder;

import javafx.stage.PopupWindow;

import java.util.function.Supplier;

public abstract class AbstractPopupWindowBuilder<T extends PopupWindow, B extends AbstractPopupWindowBuilder<T, B>> extends AbstractWindowBuilder<T, B> {

    public AbstractPopupWindowBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B autoHide(boolean autoHide) {
        target.setAutoHide(true);
        return (B) this;
    }

}
