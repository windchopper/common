package com.github.windchopper.common.fx.form;

import com.github.windchopper.common.fx.behavior.WindowApplyStoredBoundsBehavior;
import com.github.windchopper.common.fx.dialog.StageDialogFrame;
import com.github.windchopper.common.util.Pipeliner;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;
import java.util.Map;

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

    protected StageDialogFrame prepareStageDialogFrame(Image iconImage, Modality modality, boolean resizable) {
        return Pipeliner.of(Stage::new)
            .set(stage -> stage::initOwner, stage)
            .set(stage -> stage::initModality, modality)
            .add(stage -> stage::getIcons, List.of(iconImage))
            .set(stage -> stage::setResizable, resizable)
            .map(StageDialogFrame::new)
            .get();
    }

}
