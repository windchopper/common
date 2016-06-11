package name.wind.common.fx.builder;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import java.util.function.Supplier;

public class CheckBoxBuilder extends AbstractButtonBaseBuilder<CheckBox, CheckBoxBuilder> {

    public CheckBoxBuilder(Supplier<CheckBox> targetSupplier) {
        super(targetSupplier);
    }

    public CheckBoxBuilder selectedPropertyChangeListener(ChangeListener<Boolean> listener) {
        target.selectedProperty().addListener(listener);
        return this;
    }

    public CheckBoxBuilder bindSelectedProperty(ObservableValue<? extends Boolean> observable) {
        target.selectedProperty().bind(observable);
        return this;
    }

    public CheckBoxBuilder bindSelectedPropertyBidirectional(Property<Boolean> property) {
        target.selectedProperty().bindBidirectional(property);
        return this;
    }

    public CheckBoxBuilder selected(boolean selected) {
        target.setSelected(selected);
        return this;
    }

}

