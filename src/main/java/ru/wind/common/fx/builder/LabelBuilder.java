package ru.wind.common.fx.builder;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.function.Supplier;

public class LabelBuilder extends AbstractLabeledBuilder<Label, LabelBuilder> {

    public LabelBuilder(Supplier<Label> targetSupplier) {
        super(targetSupplier);
    }

    public LabelBuilder labelFor(Node node) {
        target.setLabelFor(node);
        return this;
    }

}
