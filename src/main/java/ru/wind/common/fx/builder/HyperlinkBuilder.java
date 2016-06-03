package ru.wind.common.fx.builder;

import javafx.scene.control.Hyperlink;

import java.util.function.Supplier;

public class HyperlinkBuilder extends AbstractButtonBaseBuilder<Hyperlink, HyperlinkBuilder> {

    public HyperlinkBuilder(Supplier<Hyperlink> targetSupplier) {
        super(targetSupplier);
    }

    public HyperlinkBuilder visited(boolean visited) {
        target.setVisited(visited);
        return this;
    }

}
