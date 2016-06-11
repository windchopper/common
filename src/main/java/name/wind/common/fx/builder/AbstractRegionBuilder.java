package name.wind.common.fx.builder;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;

import java.util.function.Supplier;

public abstract class AbstractRegionBuilder<T extends Region, B extends AbstractRegionBuilder<T, B>> extends AbstractParentBuilder<T, B> {

    public AbstractRegionBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    @SuppressWarnings("unchecked") public B minWidth(double minWidth) {
        target.setMinWidth(minWidth);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B preferredWidth(double preferredWidth) {
        target.setPrefWidth(preferredWidth);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B maxWidth(double maxWidth) {
        target.setMaxWidth(maxWidth);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B minHeight(double minHeight) {
        target.setMinHeight(minHeight);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B preferredHeight(double preferredHeight) {
        target.setPrefHeight(preferredHeight);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B maxHeight(double maxHeight) {
        target.setMaxHeight(maxHeight);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B padding(double padding) {
        Insets insets = new Insets(padding);
        target.setPadding(insets);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B padding(double horizontalPadding, double verticalPadding) {
        Insets insets = new Insets(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding);
        target.setPadding(insets);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B padding(double topPadding, double rightPadding, double bottomPadding, double leftPadding) {
        Insets insets = new Insets(topPadding, rightPadding, bottomPadding, leftPadding);
        target.setPadding(insets);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B background(Background background) {
        target.setBackground(background);
        return (B) this;
    }

    @SuppressWarnings("unchecked") public B border(Border border) {
        target.setBorder(border);
        return (B) this;
    }

}
