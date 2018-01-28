package com.github.windchopper.common.fx.spinner;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public enum NumberType implements SpinnableType<Number> {

    INTEGER(
        Integer.class,
        (value1st, value2nd) -> value1st.intValue() + value2nd.intValue(),
        (value1st, value2nd) -> value1st.intValue() - value2nd.intValue(),
        steps -> steps,
        NumberFormat::getIntegerInstance,
        Comparator.comparingInt(Number::intValue));

    private final Class<? extends Number> valueType;
    private final BinaryOperator<Number> adder;
    private final BinaryOperator<Number> subtractor;
    private final Function<Integer, Number> stepsConverter;
    private final Supplier<NumberFormat> format;
    private final Comparator<Number> comparator;

    NumberType(Class<? extends Number> valueType,
               BinaryOperator<Number> adder,
               BinaryOperator<Number> subtractor,
               Function<Integer, Number> stepsConverter,
               Supplier<NumberFormat> format,
               Comparator<Number> comparator) {
        this.valueType = valueType;
        this.adder = adder;
        this.subtractor = subtractor;
        this.stepsConverter = stepsConverter;
        this.format = format;
        this.comparator = comparator;
    }

    public Class<? extends Number> valueType() {
        return valueType;
    }

    @Override public Number add(Number value1st, Number value2nd) {
        return adder.apply(value1st, value2nd);
    }

    @Override public Number subtract(Number value1st, Number value2nd) {
        return subtractor.apply(value1st, value2nd);
    }

    @Override public Number steps(int steps) {
        return stepsConverter.apply(steps);
    }

    @Override public NumberFormat format() {
        return format.get();
    }

    @Override public int compare(Number value1st, Number value2nd) {
        return comparator.compare(value1st, value2nd);
    }

}
