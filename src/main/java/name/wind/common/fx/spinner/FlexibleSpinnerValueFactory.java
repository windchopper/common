package name.wind.common.fx.spinner;

import javafx.beans.NamedArg;
import javafx.scene.control.SpinnerValueFactory;

public class FlexibleSpinnerValueFactory<T> extends SpinnerValueFactory<T> {

    private final T min;
    private final T max;
    private final SpinnableType<T> spinnableType;

    public FlexibleSpinnerValueFactory(@NamedArg("spinnableType") SpinnableType<T> spinnableType,
                                       @NamedArg("min") T min,
                                       @NamedArg("max") T max,
                                       @NamedArg("value") T value) {
        this.spinnableType = spinnableType;
        this.min = min;
        this.max = max;

        setConverter(
            new SpinnableTypeConverter<>(spinnableType));

        valueProperty().addListener(
            (observable, oldValue, newValue) -> setValue(
                limitWithMinMax(newValue)));

        setValue(
            limitWithMinMax(value));
    }

    /*
     *
     */

    private T limitWithMinMax(T value) {
        return spinnableType.compare(value, min) < 0 ? min : spinnableType.compare(value, max) > 0 ? max : value;
    }

    /*
     *
     */

    @Override public void increment(int steps) {
        setValue(
            limitWithMinMax(
                spinnableType.add(
                    getValue(), spinnableType.steps(steps))));
    }

    @Override public void decrement(int steps) {
        setValue(
            limitWithMinMax(
                spinnableType.subtract(
                    getValue(), spinnableType.steps(steps))));
    }

}
