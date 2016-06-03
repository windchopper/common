package ru.wind.common.fx.builder;

import javafx.scene.control.PopupControl;

import java.util.function.Supplier;

public abstract class PopupControlBuilder<T extends PopupControl, B extends PopupControlBuilder<T, B>> extends AbstractPopupWindowBuilder<T, B> {

    public PopupControlBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

}
