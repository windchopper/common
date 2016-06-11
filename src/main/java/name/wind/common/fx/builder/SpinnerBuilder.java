package name.wind.common.fx.builder;

import javafx.beans.property.Property;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.function.Supplier;

public class SpinnerBuilder<T> extends AbstractControlBuilder<Spinner<T>, SpinnerBuilder<T>> {

    public SpinnerBuilder(Supplier<Spinner<T>> targetSupplier) {
        super(targetSupplier);
    }

    public SpinnerBuilder<T> valueFactory(SpinnerValueFactory<T> valueFactory) {
        target.setValueFactory(valueFactory);
        return this;
    }

    public SpinnerBuilder<T> editable(boolean editable) {
        target.setEditable(editable);
        return this;
    }

    public SpinnerBuilder<T> bindToValueProperty(Property<T> property) {
        property.bind(target.valueProperty());
        return this;
    }

}
