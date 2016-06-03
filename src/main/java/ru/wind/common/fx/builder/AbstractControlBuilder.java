package ru.wind.common.fx.builder;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

import java.util.function.Supplier;

public abstract class AbstractControlBuilder<T extends Control, B extends AbstractControlBuilder<T, B>> extends AbstractRegionBuilder<T, B> {

    public AbstractControlBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    public B contextMenu(ContextMenu contextMenu) {
        target.setContextMenu(contextMenu);
        return self();
    }

    public B tooltip(Tooltip tooltip) {
        target.setTooltip(tooltip);
        return self();
    }

    public B textTooltip(String tooltipText) {
        target.setTooltip(new Tooltip(tooltipText));
        return self();
    }

}
