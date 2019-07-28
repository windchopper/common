package com.github.windchopper.common.fx.form;

import com.github.windchopper.common.fx.behavior.WindowApplyStoredBoundsBehavior;
import com.github.windchopper.common.util.Pipeliner;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.stream;

public abstract class StageFormController extends FormContoller {

    protected static final Logger logger = Logger.getLogger("name.wind.application.cdi.fx");

    protected Stage stage;

    @Override protected void afterLoad(Parent form, Map<String, ?> eventParameters, Map<String, ?> formNamespace) {
        new WindowApplyStoredBoundsBehavior(getClass().getName(), this::initializeBounds)
            .apply(stage = (Stage) form.getScene().getWindow());

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

    protected Dimension2D preferredStageSize() {
        return null;
    }

    protected void initializeBounds(Window window, boolean resizable) {
        Dimension2D preferredSize = null;

        if (resizable) {
            preferredSize = preferredStageSize();
        }

        if (preferredSize == null) {
            window.sizeToScene();
        } else {
            window.setWidth(
                preferredSize.getWidth());
            window.setHeight(
                preferredSize.getHeight());
        }
    }

    protected Alert prepareAlert(Supplier<Alert> constructor) {
        return Pipeliner.of(constructor)
            .set(alert -> alert::initOwner, stage)
            .get();
    }

}
