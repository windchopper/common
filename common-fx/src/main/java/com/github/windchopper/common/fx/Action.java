package com.github.windchopper.common.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class Action {

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final StringProperty longTextProperty = new SimpleStringProperty(this, "longText");
    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>(this, "graphic");
    private final BooleanProperty externallyDisableProperty = new SimpleBooleanProperty(this, "externallyDisable");
    private final BooleanProperty internallyDisableProperty = new SimpleBooleanProperty(this, "internallyDisable");

    private final List<BiConsumer<ActionEvent, Action>> handlers = new CopyOnWriteArrayList<>();

    public <T extends EventTarget> void bindControl(ActionControlAdapter<T> controlAdapter) {
        controlAdapter.textProperty().bind(textProperty);
        controlAdapter.graphicProperty().bind(graphicProperty);

        controlAdapter.disableProperty().bind(
            Bindings.createBooleanBinding(() -> externallyDisableProperty.get() || internallyDisableProperty.get(),
                internallyDisableProperty, internallyDisableProperty));

        longTextProperty.isNotEmpty().addListener((property, oldNotEmptyState, newNotEmptyState) -> {
            var tooltip = controlAdapter.tooltipProperty().getValue();

            if (newNotEmptyState) {
                tooltip = new Tooltip();
                tooltip.textProperty().bind(longTextProperty);
            } else {
                tooltip.textProperty().unbind();
                tooltip = null;
            }

            controlAdapter.tooltipProperty().setValue(tooltip);
        });

        controlAdapter.addEventHandler(ActionEvent.ACTION, actionEvent ->
            handlers.forEach(handler -> handler.accept(actionEvent, Action.this)));
    }

    public void addHandler(BiConsumer<ActionEvent, Action> handler) {
        handlers.add(handler);
    }

    public StringProperty textProperty() {
        return textProperty;
    }

    public StringProperty longTextProperty() {
        return longTextProperty;
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphicProperty;
    }

    public BooleanProperty externallyDisableProperty() {
        return externallyDisableProperty;
    }

    public BooleanProperty internallyDisableProperty() {
        return internallyDisableProperty;
    }

}
