package ru.wind.common.fx.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.function.Supplier;

public abstract class AbstractMenuItemBuilder<T extends MenuItem, B extends AbstractMenuItemBuilder<T, B>> extends AbstractBuilder<T, B> {

    public AbstractMenuItemBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B text(String text) {
        target.setText(text);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B graphic(Node graphic) {
        target.setGraphic(graphic);
        return (B) this;
    }

    public B bindDisableProperty(ObservableValue<? extends Boolean> observable) {
        target.disableProperty().bind(observable);
        return self();
    }

}
