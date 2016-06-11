package name.wind.common.fx.builder;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.function.Supplier;

public class ContextMenuBuilder extends PopupControlBuilder<ContextMenu, ContextMenuBuilder> {

    public ContextMenuBuilder(Supplier<ContextMenu> targetSupplier) {
        super(targetSupplier);
    }

    public ContextMenuBuilder addAll(MenuItem... items) {
        target.getItems().addAll(items);
        return this;
    }

}
