package com.github.windchopper.common.fx.spinner;

import javafx.scene.control.SpinnerValueFactory;

import static java.util.Objects.requireNonNull;

public class FlexibleSpinnerValueFactory<T> extends SpinnerValueFactory<T> {

    private final T min;
    private final T max;
    private final SpinnableType<T> spinnableType;

    public FlexibleSpinnerValueFactory(SpinnableType<T> spinnableType,
                                       T min,
                                       T max,
                                       T value) {
        this.spinnableType = requireNonNull(spinnableType);
        this.min = requireNonNull(min);
        this.max = requireNonNull(max);

        setConverter(
            new SpinnableTypeConverter<>(spinnableType));

        valueProperty().addListener(
            (observable, oldValue, newValue) -> setValue(
                limitWithMinMax(newValue)));

        setValue(
            limitWithMinMax(requireNonNull(value)));
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
