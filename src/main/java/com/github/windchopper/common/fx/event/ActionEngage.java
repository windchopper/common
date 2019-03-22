package com.github.windchopper.common.fx.event;

public class ActionEngage<T> {

    private final T target;

    public ActionEngage(T target) {
        this.target = target;
    }

    public T target() {
        return target;
    }

}
