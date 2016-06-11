package name.wind.common.fx.behavior;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.addAll;

public abstract class AbstractEventDrivenBehavior<T extends Node> implements Behavior<T> {

    private final Set<EventType<?>> eventTypes = new HashSet<>(0);

    public AbstractEventDrivenBehavior(EventType<?>... eventTypeArray) {
        addAll(eventTypes, eventTypeArray);
    }

    public AbstractEventDrivenBehavior(Collection<EventType<?>> eventTypeCollection) {
        eventTypes.addAll(eventTypeCollection);
    }

    protected abstract void handleEvent(Event event);

    @Override public void apply(T target) {
        for (EventType<?> eventType : eventTypes) {
            target.addEventHandler(eventType, this::handleEvent);
        }
    }

}
