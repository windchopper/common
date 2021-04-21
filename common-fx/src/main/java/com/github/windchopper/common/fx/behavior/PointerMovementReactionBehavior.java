package com.github.windchopper.common.fx.behavior;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class PointerMovementReactionBehavior<T extends Node> extends EventDrivenBehavior<T> {

    public PointerMovementReactionBehavior() {
        super(MouseEvent.MOUSE_ENTERED, MouseEvent.MOUSE_EXITED);
    }

    protected abstract void pointerHoveredOver(T target);
    protected abstract void pointerMovedAway(T target);

    @Override protected void handleEvent(Event event) {
        @SuppressWarnings("unchecked") T target = (T) event.getSource();

        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            pointerHoveredOver(target);
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            pointerMovedAway(target);
        }
    }

}
