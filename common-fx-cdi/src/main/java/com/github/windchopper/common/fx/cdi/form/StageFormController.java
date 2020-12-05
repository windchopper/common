package com.github.windchopper.common.fx.cdi.form;

import com.github.windchopper.common.fx.behavior.WindowApplyStoredBoundsBehavior;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Map;

public abstract class StageFormController extends FormController {

    protected Stage stage;

    @Override protected void afterLoad(Parent form, Map<String, ?> parameters, Map<String, ?> formNamespace) {
        super.afterLoad(form, parameters, formNamespace);

        new WindowApplyStoredBoundsBehavior(String.format("%s-%d", getClass().getSimpleName(), getClass().getCanonicalName().hashCode()), this::initializeBounds)
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

}
