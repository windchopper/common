package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.fx.dialog.OptionDialogModel.Option;
import com.github.windchopper.common.util.Pipeliner;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.util.List;
import java.util.Optional;

public class OptionDialog<F extends DialogFrame, M extends OptionDialogModel> extends Dialog<F, M> {

    public enum Type {

        INFORMATION("dialog-information.png"),
        CONFIRM("dialog-confirm.png"),
        ERROR("dialog-error.png"),
        WARNING("dialog-warning.png");

        private final String imageFile;

        Type(String imageFile) {
            this.imageFile = imageFile;
        }

        Image image() {
            return new Image("/com/sun/javafx/scene/control/skin/modena/" + imageFile);
        }

    }

    @Override protected Pane prepareRootPane() {
        var rootPane = super.prepareRootPane();
        rootPane.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);
        return rootPane;
    }

    public static <F extends DialogFrame> Optional<Option> showOptionDialog(String message,
                                                                            Type type,
                                                                            List<Option> options,
                                                                            F frame) {
        return Pipeliner.of(OptionDialogModel::new)
            .accept(model -> Pipeliner.of(OptionDialog<F, OptionDialogModel>::new)
                .accept(dialog -> dialog.installFrame(frame))
                .accept(dialog -> dialog.installModel(model))
                .accept(dialog -> dialog.installSkeleton(Pipeliner.of(CaptionedDialogSkeleton::new)
                    .accept(skeleton -> skeleton.titleProperty().set(message))
                    .accept(skeleton -> skeleton.imageProperty().set(type.image()))
                    .get()))
                .accept(dialog -> options.forEach(option -> dialog.add(Pipeliner.of(() -> option.newAction(dialog))
                    .set(action -> action::setHandler, event -> model.setOption(option))
                    .get())))
                .accept(Dialog::show)
                .get())
            .map(model -> Optional.ofNullable(model.getOption()))
            .get();
    }

}
