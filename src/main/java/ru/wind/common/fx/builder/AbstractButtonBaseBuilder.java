package ru.wind.common.fx.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import ru.wind.common.fx.Action;

import java.util.function.Supplier;

public abstract class AbstractButtonBaseBuilder<T extends ButtonBase, B extends AbstractButtonBaseBuilder<T, B>> extends AbstractLabeledBuilder<T, B> {

    public AbstractButtonBaseBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B actionHandler(EventHandler<ActionEvent> handler) {
        target.setOnAction(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B bindAction(Action action) {
        action.bind(target);
        return (B) this;
    }

}
