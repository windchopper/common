package name.wind.common.fx.builder;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Collection;
import java.util.function.Supplier;

public class TableViewBuilder<R> extends AbstractControlBuilder<TableView<R>, TableViewBuilder<R>> {

    public TableViewBuilder(Supplier<TableView<R>> targetSupplier) {
        super(targetSupplier);
    }

    public TableViewBuilder<R> columns(TableColumn<R, ?>... columns) {
        target.getColumns().clear();
        target.getColumns().addAll(columns);
        return self();
    }

    public TableViewBuilder<R> items(R... items) {
        target.getItems().clear();
        target.getItems().addAll(items);
        return this;
    }

    public TableViewBuilder<R> items(Collection<R> items) {
        target.getItems().clear();
        target.getItems().addAll(items);
        return this;
    }

    public TableViewBuilder<R> editable(boolean editable) {
        target.setEditable(editable);
        return this;
    }

}
