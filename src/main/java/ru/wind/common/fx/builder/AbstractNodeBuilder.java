package ru.wind.common.fx.builder;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ru.wind.common.fx.behavior.Behavior;

import java.util.function.Supplier;

public abstract class AbstractNodeBuilder<T extends Node, B extends AbstractNodeBuilder<T, B>> extends AbstractBuilder<T, B> {

    public AbstractNodeBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    public B identifier(String identifier) {
        target.setId(identifier);
        return self();
    }

    public B applyBehavior(Behavior<? super T> behavior) {
        behavior.apply(target);
        return self();
    }

    @SuppressWarnings("unchecked") public B disable(boolean disable) {
        target.setDisable(disable);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B bindDisableProperty(ObservableValue<? extends Boolean> observable) {
        target.disableProperty().bind(observable);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B bindVisibleProperty(ObservableValue<? extends Boolean> observable) {
        target.visibleProperty().bind(observable);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B visible(boolean visible) {
        target.setVisible(visible);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B style(String style) {
        target.setStyle(style);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B addStyleClasses(String... styleClasses) {
        target.getStyleClass().addAll(styleClasses);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B effect(Effect effect) {
        target.setEffect(effect);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B effect(Supplier<Effect> effectSupplier) {
        target.setEffect(effectSupplier.get());
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B pickOnBounds(boolean pickOnBounds) {
        target.setPickOnBounds(pickOnBounds);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B hboxHorizontalGrow(Priority priority) {
        HBox.setHgrow(target, priority);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B hboxMargin(Insets margin) {
        HBox.setMargin(target, margin);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B hboxMargin(double top, double right, double bottom, double left) {
        HBox.setMargin(target, new Insets(top, right, bottom, left));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B hboxMargin(double horizontalMargin, double verticalMargin) {
        HBox.setMargin(target, new Insets(verticalMargin, horizontalMargin, verticalMargin, horizontalMargin));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B hboxMargin(double margin) {
        HBox.setMargin(target, new Insets(margin));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B vboxMargin(Insets margin) {
        VBox.setMargin(target, margin);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B vboxMargin(double top, double right, double bottom, double left) {
        VBox.setMargin(target, new Insets(top, right, bottom, left));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B vboxMargin(double horizontal, double vertical) {
        VBox.setMargin(target, new Insets(vertical, horizontal, vertical, horizontal));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B vboxMargin(double margin) {
        VBox.setMargin(target, new Insets(margin));
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mouseTransparent(boolean mouseTransparent) {
        target.setMouseTransparent(mouseTransparent);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public <E extends Event> B eventHandler(EventType<E> type, EventHandler<E> handler) {
        target.addEventHandler(type, handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public <E extends Event> B eventFilter(EventType<E> type, EventHandler<E> filter) {
        target.addEventFilter(type, filter);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B keyPressedHandler(EventHandler<KeyEvent> handler) {
        target.setOnKeyPressed(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B keyPressedHandler(KeyCode keyCode, EventHandler<KeyEvent> handler) {
        target.setOnKeyPressed(
            event -> {
                if (event.getCode() == keyCode) {
                    handler.handle(event);
                }
            }
        );

        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mouseClickedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseClicked(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mouseEnteredHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseEntered(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mouseExitedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseExited(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mousePressedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMousePressed(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B mouseDraggedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseDragged(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B contextMenuRequestedHandler(EventHandler<ContextMenuEvent> handler) {
        target.setOnContextMenuRequested(handler);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B cursor(Cursor cursor) {
        target.setCursor(cursor);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B defaultCursor() {
        target.setCursor(Cursor.DEFAULT);
        return (B) this;
    }

}
