package name.wind.common.fx.builder;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

import java.util.function.Supplier;

public class TitledPaneBuilder extends AbstractLabeledBuilder<TitledPane, TitledPaneBuilder> {

    public TitledPaneBuilder(Supplier<TitledPane> targetSupplier) {
        super(targetSupplier);
    }

    public TitledPaneBuilder content(Node node) {
        target.setContent(node);
        return this;
    }

    public TitledPaneBuilder collapsible(boolean collapsible) {
        target.setCollapsible(collapsible);
        return this;
    }

    public TitledPaneBuilder animated(boolean animated) {
        target.setAnimated(animated);
        return this;
    }

}
