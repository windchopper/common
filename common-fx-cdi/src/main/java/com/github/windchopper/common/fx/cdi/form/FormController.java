package com.github.windchopper.common.fx.cdi.form;

import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.stream;

public abstract class FormController {

    protected static final Logger logger = Logger.getLogger(FormController.class.getName());

    protected void afterLoad(Parent form, Map<String, ?> parameters, Map<String, ?> formNamespace) {
        stream(getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
                Object value = formNamespace.get(field.getName());

                if (value != null) {
                    try {
                        field.setAccessible(true);
                        field.set(this, value);
                    } catch (SecurityException | IllegalAccessException thrown) {
                        logger.log(
                            Level.WARNING,
                            thrown.getMessage(),
                            thrown);
                    }
                }
            });
    }

}
