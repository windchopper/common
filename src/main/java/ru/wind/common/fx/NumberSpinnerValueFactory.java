package ru.wind.common.fx;

import javafx.beans.NamedArg;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NumberSpinnerValueFactory extends SpinnerValueFactory<Number> {

    private enum NumberType {

        INTEGER(Integer.class) {

            @Override public Number add(Number number1st, Number number2nd) {
                return number1st.intValue() + number2nd.intValue();
            }

            @Override public Number subtract(Number number1st, Number number2nd) {
                return number1st.intValue() - number2nd.intValue();
            }

            @Override public NumberFormat format() {
                return NumberFormat.getIntegerInstance();
            }

        };

        private final Class<? extends Number> numberClass;

        NumberType(Class<? extends Number> numberClass) {
            this.numberClass = numberClass;
        }

        public abstract Number add(Number number1st, Number number2nd);
        public abstract Number subtract(Number number1st, Number number2nd);

        public abstract NumberFormat format();

        public static NumberType valueOf(Class<? extends Number> numberClass) {
            for (NumberType value : values()) {
                if (numberClass == value.numberClass) return value;
            }

            throw new IllegalArgumentException(
                numberClass.getName()
            );
        }

    }

    private static final Logger logger = Logger.getLogger(
        NumberSpinnerValueFactory.class.getName()
    );

    private class NumberConverter extends StringConverter<Number> {

        private final NumberFormat numberFormat;

        private NumberConverter(NumberType numberType) {
            numberFormat = numberType.format();
        }

        @Override public String toString(Number number) {
            return numberFormat.format(number);
        }

        @Override public Number fromString(String string) {
            try {
                return numberFormat.parse(string);
            } catch (ParseException thrown) {
                if (logger.isLoggable(Level.WARNING)) {
                    logger.log(Level.WARNING, thrown::getMessage);
                }

                return BigDecimal.ZERO;
            }
        }

    }

    private final Number min;
    private final Number max;
    private final NumberType numberType;

    public <T extends Number> NumberSpinnerValueFactory(@NamedArg("min")  T min, @NamedArg("max") T max, @NamedArg("value") T value) {
        numberType = NumberType.valueOf(
            value.getClass()
        );

        this.min = min;
        this.max = max;

        setConverter(
            new NumberConverter(numberType)
        );

        valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                setValue(
                    ensureInRange(newValue)
                );
            }
        );

        setValue(
            ensureInRange(value)
        );
    }

    Number ensureInRange(Number value) {
        return value.doubleValue() < min.doubleValue() ? min : value.doubleValue() > max.doubleValue() ? max : value;
    }

    @Override public void increment(int steps) {
        setValue(
            ensureInRange(
                numberType.add(
                    getValue(), steps
                )
            )
        );
    }

    @Override public void decrement(int steps) {
        setValue(
            ensureInRange(
                numberType.subtract(
                    getValue(), steps
                )
            )
        );
    }

}
