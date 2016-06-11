package name.wind.common.fx.builder;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.function.Supplier;

public class TreeViewBuilder<T> extends AbstractControlBuilder<TreeView<T>, TreeViewBuilder<T>> {

    public TreeViewBuilder(Supplier<TreeView<T>> targetSupplier) {
        super(targetSupplier);
    }

    public TreeViewBuilder<T> root(TreeItem<T> root) {
        target.setRoot(root);
        return this;
    }

    public TreeViewBuilder<T> showRoot(boolean showRoot) {
        target.setShowRoot(showRoot);
        return this;
    }

    public TreeViewBuilder<T> cellFactory(Callback<TreeView<T>, TreeCell<T>> cellFactory) {
        target.setCellFactory(cellFactory);
        return this;
    }


    public TreeViewBuilder<T> dragDetectedHandler(EventHandler<MouseEvent> dragDetectedHandler) {
        target.setOnDragDetected(dragDetectedHandler);
        return this;
    }

}
