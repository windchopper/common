package ru.wind.common.fx.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import java.util.function.Supplier;

public class MenuItemBuilder extends AbstractMenuItemBuilder<MenuItem, MenuItemBuilder> {

    public MenuItemBuilder(Supplier<MenuItem> targetSupplier) {
        super(targetSupplier);
    }

    public MenuItemBuilder actionHandler(EventHandler<ActionEvent> handler) {
        target.setOnAction(handler);
        return self();
    }

}
