package ru.wind.common.fx.builder;

import javafx.geometry.Insets;

public class InsetsBuilder extends AbstractLazyBuilder<Insets, InsetsBuilder> {

    private double top;
    private double right;
    private double bottom;
    private double left;

    private Insets target;

    public InsetsBuilder top(double top) {
        this.top = top;
        return self();
    }

    public InsetsBuilder right(double right) {
        this.right = right;
        return self();
    }

    public InsetsBuilder bottom(double bottom) {
        this.bottom = bottom;
        return self();
    }

    public InsetsBuilder left(double left) {
        this.left = left;
        return self();
    }

    public InsetsBuilder horizontal(double horizontal) {
        right = left = horizontal;
        return self();
    }

    public InsetsBuilder vertical(double vertical) {
        top = bottom = vertical;
        return self();
    }

    public InsetsBuilder all(double all) {
        top = right = bottom = left = all;
        return self();
    }

    @Override public Insets get() {
        if (target == null) {
            target = new Insets(top, right, bottom, left);
        }

        return target;
    }

}
