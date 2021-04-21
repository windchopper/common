package com.github.windchopper.common.fx.behavior;

import javafx.scene.Node;

public class CompositeBehavior<T extends Node> implements Behavior<T> {

    private final Behavior<T>[] behaviors;

    @SafeVarargs public CompositeBehavior(Behavior<T>... behaviors) {
        this.behaviors = behaviors;
    }

    @Override public void apply(T target) {
        for (var behavior : behaviors) {
            behavior.apply(target);
        }
    }

}
