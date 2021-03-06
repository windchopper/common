package com.github.windchopper.common.fx.spinner;

import javafx.util.StringConverter;

import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpinnableTypeConverter<T> extends StringConverter<T> {

    private static final Logger logger = Logger.getLogger(SpinnableTypeConverter.class.getName());
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private final SpinnableType<T> spinnableType;

    public SpinnableTypeConverter(SpinnableType<T> spinnableType) {
        this.spinnableType = spinnableType;
    }

    @Override @SuppressWarnings("unchecked") public T fromString(String string) {
        try {
            return (T) spinnableType.format().parseObject(string);
        } catch (ParseException thrown) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, String.format(bundle.getString("com.github.windchopper.common.fx.spinner.SpinnableTypeConverter.parseError"), string), thrown);
            }

            return null;
        }
    }

    @Override public String toString(T object) {
        return spinnableType.format().format(object);
    }

}
