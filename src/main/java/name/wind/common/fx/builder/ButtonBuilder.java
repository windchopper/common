package name.wind.common.fx.builder;

import javafx.scene.control.Button;

import java.util.function.Supplier;

public class ButtonBuilder extends AbstractButtonBaseBuilder<Button, ButtonBuilder> {

    public ButtonBuilder(Supplier<Button> targetSupplier) {
        super(targetSupplier);
    }

    public ButtonBuilder defaultButton() {
        target.setDefaultButton(true);
        return this;
    }

    public ButtonBuilder defaultButton(boolean defaultButton) {
        target.setDefaultButton(defaultButton);
        return this;
    }

    public ButtonBuilder cancelButton() {
        target.setCancelButton(true);
        return this;
    }

    public ButtonBuilder cancelButton(boolean cancelButton) {
        target.setCancelButton(cancelButton);
        return this;
    }

}
