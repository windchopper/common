package name.wind.common.fx.builder;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class AbstractLabeledBuilder<T extends Labeled, B extends AbstractLabeledBuilder<T, B>> extends AbstractControlBuilder<T, B> {

    public AbstractLabeledBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    public B text(String text) {
        target.setText(text);
        return self();
    }

    public B bundleText(ResourceBundle bundle, String key) {
        target.setText(bundle.getString(key));
        return self();
    }

    public B alignment(Pos alignment) {
        target.setAlignment(alignment);
        return self();
    }

    public B textFill(Paint textFill) {
        target.setTextFill(textFill);
        return self();
    }

    public B graphic(Node graphic) {
        target.setGraphic(graphic);
        return self();
    }

    public B graphicTextGap(double gap) {
        target.setGraphicTextGap(gap);
        return self();
    }

    public B font(Font font) {
        target.setFont(font);
        return self();
    }

    @SuppressWarnings("unchecked") public B derivedFont(UnaryOperator<Font> fontOperator) {
        target.setFont(
            fontOperator.apply(
                target.getFont()
            )
        );

        return (B) this;
    }

    @SuppressWarnings("unchecked") public B wrapText() {
        target.setWrapText(true);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B wrapText(boolean wrapText) {
        target.setWrapText(wrapText);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B bindTextProperty(ObservableValue<? extends String> observable) {
        target.textProperty().bind(observable);
        return (B) this;
    }

}
