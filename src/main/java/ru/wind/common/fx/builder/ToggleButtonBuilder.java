package ru.wind.common.fx.builder;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.util.function.Supplier;

public class ToggleButtonBuilder extends AbstractButtonBaseBuilder<ToggleButton, ToggleButtonBuilder> {

    public ToggleButtonBuilder(Supplier<ToggleButton> targetSupplier) {
        super(targetSupplier);
    }

    public ToggleButtonBuilder toggleGroup(ToggleGroup toggleGroup) {
        target.setToggleGroup(toggleGroup);
        return this;
    }

}
