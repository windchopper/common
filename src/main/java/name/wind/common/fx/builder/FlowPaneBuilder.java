package name.wind.common.fx.builder;

import javafx.scene.layout.FlowPane;

import java.util.function.Supplier;

public class FlowPaneBuilder extends AbstractPaneBuilder<FlowPane, FlowPaneBuilder> {

    public FlowPaneBuilder(Supplier<FlowPane> targetSupplier) {
        super(targetSupplier);
    }

}
