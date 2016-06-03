package ru.wind.common.fx.builder;

import javafx.scene.control.MenuItem;

import java.util.function.Supplier;

public class MenuItemBuilder extends AbstractMenuItemBuilder<MenuItem, MenuItemBuilder> {

    public MenuItemBuilder(Supplier<MenuItem> targetSupplier) {
        super(targetSupplier);
    }

}
