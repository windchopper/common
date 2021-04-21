package com.github.windchopper.common.fx.behavior;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

import java.util.*;

import static java.util.Collections.addAll;

public abstract class EventDrivenBehavior<T extends Node> implements Behavior<T> {

    private final Set<EventType<?>> eventTypes = new HashSet<>();

    public EventDrivenBehavior(EventType<?>... eventTypeArray) {
        addAll(eventTypes, eventTypeArray);
    }

    public EventDrivenBehavior(Collection<EventType<?>> eventTypeCollection) {
        eventTypes.addAll(eventTypeCollection);
    }

    protected abstract void handleEvent(Event event);

    @Override public void apply(T target) {
        for (var eventType : eventTypes) {
            target.addEventHandler(eventType, this::handleEvent);
        }
    }

}
