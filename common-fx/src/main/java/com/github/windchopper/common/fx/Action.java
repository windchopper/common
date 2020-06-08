package com.github.windchopper.common.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Action {

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final StringProperty longTextProperty = new SimpleStringProperty(this, "longText");
    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>(this, "graphic");
    private final BooleanProperty externallyDisableProperty = new SimpleBooleanProperty(this, "externallyDisable");
    private final BooleanProperty internallyDisableProperty = new SimpleBooleanProperty(this, "internallyDisable");

    @FunctionalInterface public interface EventHandler {
        void handle(ActionEvent actionEvent, Action action);
    }

    private final List<EventHandler> handlers = new CopyOnWriteArrayList<>();

    public <T extends EventTarget> void bindControl(ActionControlAdapter<T> actionControlAdapter) {
        actionControlAdapter.textProperty().bind(textProperty);
        actionControlAdapter.graphicProperty().bind(graphicProperty);

        actionControlAdapter.disableProperty().bind(Bindings.createBooleanBinding(
            () -> externallyDisableProperty.get() || internallyDisableProperty.get(),
            internallyDisableProperty, internallyDisableProperty));

        longTextProperty.isNotEmpty().addListener((property, oldNotEmptyState, newNotEmptyState) -> {
            var tooltip = actionControlAdapter.toolipProperty().getValue();

            if (newNotEmptyState) {
                tooltip = new Tooltip();
                tooltip.textProperty().bind(longTextProperty);
            } else {
                tooltip.textProperty().unbind();
                tooltip = null;
            }

            actionControlAdapter.toolipProperty().setValue(tooltip);
        });

        actionControlAdapter.addEventHandler(ActionEvent.ACTION, actionEvent ->
            handlers.forEach(handler -> handler.handle(actionEvent, Action.this)));
    }

    public void addHandler(EventHandler handler) {
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
