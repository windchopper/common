package com.github.windchopper.common.fx.form;

import com.github.windchopper.common.fx.behavior.WindowApplyStoredBoundsBehavior;
import com.github.windchopper.common.util.Pipeliner;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Map;
import java.util.function.Supplier;

public abstract class StageFormController extends FormContoller {

    protected Stage stage;

    @Override protected void afterLoad(Parent form, Map<String, ?> parameters, Map<String, ?> formNamespace) {
        super.afterLoad(form, parameters, formNamespace);

        new WindowApplyStoredBoundsBehavior(getClass().getName(), this::initializeBounds)
            .apply(stage = (Stage) form.getScene().getWindow());
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
