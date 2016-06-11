package name.wind.common.fx.builder;

import javafx.scene.control.PasswordField;

import java.util.function.Supplier;

public class PasswordFieldBuilder extends AbstractTextInputControlBuilder<PasswordField, PasswordFieldBuilder> {

    public PasswordFieldBuilder(Supplier<PasswordField> targetSupplier) {
        super(targetSupplier);
    }

}
