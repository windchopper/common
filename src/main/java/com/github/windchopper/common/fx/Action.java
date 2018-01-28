package com.github.windchopper.common.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

import java.util.concurrent.Executor;

public class Action implements EventHandler<ActionEvent> {

    private static final String PROPERTY__ACTION = "com.github.windchopper.common.fx.Action";

    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private final StringProperty longTextProperty = new SimpleStringProperty(this, "longText");
    private final ObjectProperty<Node> graphicProperty = new SimpleObjectProperty<>(this, "graphic");
    private final BooleanProperty disabledExternallyProperty = new SimpleBooleanProperty(this, "disabledExternally");
    private final BooleanProperty disabledInternallyProperty = new SimpleBooleanProperty(this, "disabledInternally");

    private boolean reenterable;
    private Executor executor;
    private EventHandler<ActionEvent> handler;

    public boolean isReenterable() {
        return reenterable;
    }

    public void setReenterable(boolean reenterable) {
        this.reenterable = reenterable;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public EventHandler<ActionEvent> getHandler() {
        return handler;
    }

    public void setHandler(EventHandler<ActionEvent> handler) {
        this.handler = handler;
    }

    /*
     *
     */

    @FunctionalInterface public interface AddEventHandlerMethod {
        <E extends Event> void invoke(EventType<E> eventType, EventHandler<E> eventHandler);
    }

    public void bindManually(Property<String> widgetTextProperty,
                             Property<? super Node> widgetGraphicProperty,
                             Property<Boolean> widgetDisableProperty,
                             Property<Tooltip> widgetTooltipProperty,
                             AddEventHandlerMethod widgetAddEventHandlerMethod) {

        if (widgetDisableProperty != null) widgetTextProperty.bind(textProperty);
        if (widgetGraphicProperty != null) widgetGraphicProperty.bind(graphicProperty);
        if (widgetDisableProperty != null) widgetDisableProperty.bind(Bindings.createBooleanBinding(
            () -> disabledExternallyProperty.get() || disabledInternallyProperty.get(),
            disabledInternallyProperty, disabledExternallyProperty));

        if (widgetTooltipProperty != null) {
            longTextProperty.isNotEmpty().addListener((property, oldNotEmptyState, newNotEmptyState) -> {
                Tooltip tooltip = widgetTooltipProperty.getValue();

                if (newNotEmptyState) {
                    tooltip = new Tooltip();
                    tooltip.textProperty().bind(longTextProperty);
                } else {
                    tooltip.textProperty().unbind();
                    tooltip = null;
                }

                widgetTooltipProperty.setValue(tooltip);
            });
        }

        if (widgetAddEventHandlerMethod != null) {
            widgetAddEventHandlerMethod.invoke(ActionEvent.ACTION, this);
        }
    }

    public void bind(ButtonBase button) {
        bindManually(button.textProperty(), button.graphicProperty(), button.disableProperty(), button.tooltipProperty(),
            button::addEventHandler);
    }

    public void bind(MenuItem menuItem) {
        bindManually(menuItem.textProperty(), menuItem.graphicProperty(), menuItem.disableProperty(), null,
            menuItem::addEventHandler);
    }

    /*
     *
     */

    public StringProperty textProperty() {
        return textProperty;
    }

    public StringProperty longTextProperty() {
        return longTextProperty;
    }

    public ObjectProperty<Node> graphicProperty() {
        return graphicProperty;
    }

    public BooleanProperty disabledProperty() {
        return disabledExternallyProperty;
    }

    /*
     * EventHandler implementation
     */

    @Override public void handle(ActionEvent event) {
        disabledInternallyProperty.set(
            !reenterable);

        Runnable runnable = () -> {
            try {
                handler.handle(event);
            } finally {
                disabledInternallyProperty.set(false);
            }
        };

        if (executor != null) {
            executor.execute(runnable);
        } else {
            runnable.run();
        }
    }

}
