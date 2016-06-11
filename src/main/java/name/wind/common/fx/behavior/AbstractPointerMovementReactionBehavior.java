package name.wind.common.fx.behavior;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class AbstractPointerMovementReactionBehavior<T extends Node> extends AbstractEventDrivenBehavior<T> {

    public AbstractPointerMovementReactionBehavior() {
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
