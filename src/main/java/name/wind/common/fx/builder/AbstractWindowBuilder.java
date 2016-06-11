package name.wind.common.fx.builder;

import javafx.stage.Window;
import name.wind.common.preferences.SimplePreferencesEntry;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractWindowBuilder<T extends Window, B extends AbstractWindowBuilder<T, B>> extends AbstractBuilder<T, B> {

    protected AbstractWindowBuilder(Supplier<T> targetSupplier) {
        super(targetSupplier);
    }

    public B preferredLocationAndSize(
        double preferredX, double preferredY, double preferredWidth, double preferredHeight,
        SimplePreferencesEntry<Double> savedX, SimplePreferencesEntry<Double> savedY, SimplePreferencesEntry<Double> savedWidth, SimplePreferencesEntry<Double> savedHeight) {

        target.xProperty().addListener((property, oldX, newX) -> savedX.accept(newX.doubleValue()));
        target.yProperty().addListener((property, oldY, newY) -> savedY.accept(newY.doubleValue()));
        target.widthProperty().addListener((property, oldWidth, newWidth) -> savedWidth.accept(newWidth.doubleValue()));
        target.heightProperty().addListener((property, oldHeight, newHeight) -> savedHeight.accept(newHeight.doubleValue()));

        target.setX(Optional.ofNullable(savedX.get()).orElse(preferredX));
        target.setY(Optional.ofNullable(savedY.get()).orElse(preferredY));
        target.setWidth(Optional.ofNullable(savedWidth.get()).orElse(preferredWidth));
        target.setHeight(Optional.ofNullable(savedHeight.get()).orElse(preferredHeight));

        return self();
    }

}
