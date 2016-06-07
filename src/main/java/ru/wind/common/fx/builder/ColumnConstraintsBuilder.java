package ru.wind.common.fx.builder;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

import java.util.function.Supplier;

public class ColumnConstraintsBuilder extends AbstractBuilder<ColumnConstraints, ColumnConstraintsBuilder> {

    public ColumnConstraintsBuilder(Supplier<ColumnConstraints> targetSupplier) {
        super(targetSupplier);
    }

    public ColumnConstraintsBuilder fillWidth(boolean fillWidth) {
        target.setHgrow(fillWidth ? Priority.ALWAYS : Priority.NEVER);
        target.setFillWidth(fillWidth);
        return this;
    }

}
