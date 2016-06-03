package ru.wind.common.fx.behavior;

import javafx.scene.Node;

@FunctionalInterface public interface Behavior<T extends Node> {

    void apply(T target);

}
