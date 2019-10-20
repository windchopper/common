package com.github.windchopper.common.fx.behavior;

import javafx.scene.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.addAll;

public class CompositeBehavior<T extends Node> implements Behavior<T> {

    private final Set<Behavior<T>> behaviors = new HashSet<>(0);

    @SafeVarargs public CompositeBehavior(Behavior<T>... behaviorArray) {
        addAll(behaviors, behaviorArray);
    }

    public CompositeBehavior(Collection<Behavior<T>> behaviorCollection) {
        behaviors.addAll(behaviorCollection);
    }

    @Override public void apply(T target) {
        for (var behavior : behaviors) {
            behavior.apply(target);
        }
    }

}
