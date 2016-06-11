package name.wind.common.fx.builder;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.function.Supplier;

public class VBoxBuilder extends AbstractPaneBuilder<VBox, VBoxBuilder> {

    public VBoxBuilder(Supplier<VBox> targetSupplier) {
        super(targetSupplier);
    }

    public VBoxBuilder alignment(Pos alignment) {
        target.setAlignment(alignment);
        return this;
    }

    public VBoxBuilder spacing(double spacing) {
        target.setSpacing(spacing);
        return this;
    }

    public VBoxBuilder addSeparator() {
        target.getChildren().add(
            new Separator(Orientation.HORIZONTAL)
        );

        return this;
    }

}
