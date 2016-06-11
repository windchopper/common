package name.wind.common.fx.builder;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Separator;

import java.util.function.Supplier;

public class SeparatorBuilder extends AbstractControlBuilder<Separator, SeparatorBuilder> {

    public SeparatorBuilder(Supplier<Separator> targetSupplier) {
        super(targetSupplier);
    }

    public SeparatorBuilder orientation(Orientation orientation) {
        target.setOrientation(orientation);
        return this;
    }

    public SeparatorBuilder horizontalAlignment(HPos horizontalAlignment) {
        target.setHalignment(horizontalAlignment);
        return this;
    }

    public SeparatorBuilder verticalAlignment(VPos verticalAlignment) {
        target.setValignment(verticalAlignment);
        return this;
    }

}
