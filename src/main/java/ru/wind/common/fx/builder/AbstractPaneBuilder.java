package ru.wind.common.fx.builder;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.function.Supplier;

public abstract class AbstractPaneBuilder<T extends Pane, B extends AbstractPaneBuilder<T, B>> extends AbstractRegionBuilder<T, B> {

    public AbstractPaneBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B add(Node node) {
        target.getChildren().add(node);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B addAll(Node... nodes) {
        target.getChildren().addAll(nodes);
        return (B) this;
    }

}
