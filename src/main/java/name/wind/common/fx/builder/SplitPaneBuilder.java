package name.wind.common.fx.builder;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.util.function.Supplier;

public class SplitPaneBuilder extends AbstractControlBuilder<SplitPane, SplitPaneBuilder> {

    public SplitPaneBuilder(Supplier<SplitPane> targetSupplier) {
        super(targetSupplier);
    }

    public SplitPaneBuilder orientation(Orientation orientation) {
        target.setOrientation(orientation);
        return self();
    }

    public SplitPaneBuilder add(Node node) {
        target.getItems().add(node);
        return self();
    }

    public SplitPaneBuilder addAll(Node... nodes) {
        target.getItems().addAll(nodes);
        return self();
    }

}
