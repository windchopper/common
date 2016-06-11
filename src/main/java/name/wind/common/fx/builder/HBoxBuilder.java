package name.wind.common.fx.builder;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.util.function.Supplier;

public class HBoxBuilder extends AbstractPaneBuilder<HBox, HBoxBuilder> {

    public HBoxBuilder(Supplier<HBox> targetSupplier) {
        super(targetSupplier);
    }

    public HBoxBuilder alignment(Pos alignment) {
        target.setAlignment(alignment);
        return this;
    }

    public HBoxBuilder spacing(double spacing) {
        target.setSpacing(spacing);
        return this;
    }

    public HBoxBuilder addSeparator() {
        target.getChildren().add(
            new Separator(Orientation.VERTICAL)
        );

        return this;
    }

}
