package name.wind.common.fx.builder;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;

import java.util.function.Supplier;

public abstract class AbstractTextInputControlBuilder<T extends TextInputControl, B extends AbstractTextInputControlBuilder<T, B>> extends AbstractControlBuilder<T, B> {

    public AbstractTextInputControlBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    public B editable(boolean editable) {
        target.setEditable(editable);
        return self();
    }

    public B text(String text) {
        target.setText(text);
        return self();
    }

    public B bindTextProperty(ObservableValue<String> observable) {
        target.textProperty().bind(observable);
        return self();
    }

    public B bindTextPropertyBidirectional(Property<String> observable) {
        target.textProperty().bindBidirectional(observable);
        return self();
    }

    public B textPropertyChangeListener(ChangeListener<String> listener) {
        target.textProperty().addListener(listener);
        return self();
    }

    public B promptText(String promptText) {
        target.setPromptText(promptText);
        return self();
    }

}
