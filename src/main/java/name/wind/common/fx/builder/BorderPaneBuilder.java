package name.wind.common.fx.builder;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.function.Supplier;

public class BorderPaneBuilder extends AbstractPaneBuilder<BorderPane, BorderPaneBuilder> {

    public BorderPaneBuilder(Supplier<BorderPane> targetSupplier) {
        super(targetSupplier);
    }

    public BorderPaneBuilder top(Node node) {
        target.setTop(node);
        return this;
    }

    public BorderPaneBuilder center(Node node) {
        target.setCenter(node);
        return this;
    }

    public BorderPaneBuilder bottom(Node node) {
        target.setBottom(node);
        return this;
    }

}
