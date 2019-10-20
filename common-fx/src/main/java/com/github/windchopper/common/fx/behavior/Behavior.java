package com.github.windchopper.common.fx.behavior;

@FunctionalInterface public interface Behavior<T> {

    void apply(T target);

}
