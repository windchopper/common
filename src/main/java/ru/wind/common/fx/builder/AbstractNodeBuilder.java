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

    public B disable(boolean disable) {
        target.setDisable(disable);
        return self();
    }

    public B bindDisableProperty(ObservableValue<? extends Boolean> observable) {
        target.disableProperty().bind(observable);
        return self();
    }

    public B visible(boolean visible) {
        target.setVisible(visible);
        return self();
    }

    public B bindVisibleProperty(ObservableValue<? extends Boolean> observable) {
        target.visibleProperty().bind(observable);
        return self();
    }

    public B style(String style) {
        target.setStyle(style);
        return self();
    }

    public B addStyleClasses(String... styleClasses) {
        target.getStyleClass().addAll(styleClasses);
        return self();
    }

    public B effect(Effect effect) {
        target.setEffect(effect);
        return self();
    }

    public B effect(Supplier<Effect> effectSupplier) {
        target.setEffect(effectSupplier.get());
        return self();
    }

    public B pickOnBounds(boolean pickOnBounds) {
        target.setPickOnBounds(pickOnBounds);
        return self();
    }

    public B hboxHorizontalGrow(Priority priority) {
        HBox.setHgrow(target, priority);
        return self();
    }

    public B hboxMargin(Insets margin) {
        HBox.setMargin(target, margin);
        return self();
    }

    public B hboxMargin(double top, double right, double bottom, double left) {
        HBox.setMargin(target, new Insets(top, right, bottom, left));
        return self();
    }

    public B hboxMargin(double horizontalMargin, double verticalMargin) {
        HBox.setMargin(target, new Insets(verticalMargin, horizontalMargin, verticalMargin, horizontalMargin));
        return self();
    }

    public B hboxMargin(double margin) {
        HBox.setMargin(target, new Insets(margin));
        return self();
    }

    public B vboxMargin(Insets margin) {
        VBox.setMargin(target, margin);
        return self();
    }

    public B vboxMargin(double top, double right, double bottom, double left) {
        VBox.setMargin(target, new Insets(top, right, bottom, left));
        return self();
    }

    public B vboxMargin(double horizontal, double vertical) {
        VBox.setMargin(target, new Insets(vertical, horizontal, vertical, horizontal));
        return self();
    }

    public B vboxMargin(double margin) {
        VBox.setMargin(target, new Insets(margin));
        return self();
    }

    public B mouseTransparent(boolean mouseTransparent) {
        target.setMouseTransparent(mouseTransparent);
        return self();
    }

    public <E extends Event> B eventHandler(EventType<E> type, EventHandler<E> handler) {
        target.addEventHandler(type, handler);
        return self();
    }

    public <E extends Event> B eventFilter(EventType<E> type, EventHandler<E> filter) {
        target.addEventFilter(type, filter);
        return self();
    }

    public B keyPressedHandler(EventHandler<KeyEvent> handler) {
        target.setOnKeyPressed(handler);
        return self();
    }

    public B keyPressedHandler(KeyCode keyCode, EventHandler<KeyEvent> handler) {
        target.setOnKeyPressed(
            event -> {
                if (event.getCode() == keyCode) {
                    handler.handle(event);
                }
            }
        );

        return self();
    }

    public B mouseClickedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseClicked(handler);
        return self();
    }

    public B mouseEnteredHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseEntered(handler);
        return self();
    }

    public B mouseExitedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseExited(handler);
        return self();
    }

    public B mousePressedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMousePressed(handler);
        return self();
    }

    public B mouseDraggedHandler(EventHandler<MouseEvent> handler) {
        target.setOnMouseDragged(handler);
        return self();
    }

    public B contextMenuRequestedHandler(EventHandler<ContextMenuEvent> handler) {
        target.setOnContextMenuRequested(handler);
        return self();
    }

    public B cursor(Cursor cursor) {
        target.setCursor(cursor);
        return self();
    }

    public B defaultCursor() {
        target.setCursor(Cursor.DEFAULT);
        return self();
    }

}
