package com.github.windchopper.common.fx.application.fx;

import com.github.windchopper.common.fx.behavior.WindowApplyStoredBoundsBehavior;
import com.github.windchopper.common.fx.search.FormIterator;
import com.github.windchopper.common.util.Pipeliner;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.stream;

public abstract class StageController {

    protected static final Logger logger = Logger.getLogger("name.wind.application.cdi.fx");

    protected Stage stage;

    protected void start(Stage stage, String fxmlResource, Map<String, ?> parameters) {
        new WindowApplyStoredBoundsBehavior(fxmlResource, this::initializeBounds)
            .apply(this.stage = stage);

        stream(getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(FXML.class)).forEach(field -> {
                for (Iterator<Object> i = new FormIterator(stage.getScene()); i.hasNext(); ) {
                    Object formElement = i.next();
                    if (formElement instanceof Styleable && field.getName().equalsIgnoreCase(((Styleable) formElement).getId())) {
                        try {
                            field.setAccessible(true);
                            field.set(this, formElement);
                        } catch (SecurityException | IllegalAccessException thrown) {
                            logger.log(
                                Level.WARNING,
                                thrown.getMessage(),
                                thrown);
                        }
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
