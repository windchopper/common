package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.fx.dialog.OptionDialog.Type;
import com.github.windchopper.common.fx.dialog.OptionDialogModel.Option;
import com.github.windchopper.common.util.Pipeliner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static java.util.Arrays.asList;

public class DialogExample extends Application {

    private Stage primaryStage;

    @Override public void start(Stage primaryStage) {
        Pipeliner.of(() -> this.primaryStage = primaryStage)
            .set(stage -> stage::setScene, Pipeliner.of(() -> new Scene(
                Pipeliner.of(FlowPane::new)
                    .set(pane -> pane::setPadding, new Insets(10.0))
                    .set(pane -> pane::setHgap, 5.0)
                    .add(pane -> pane::getChildren, asList(
                        Pipeliner.of(Button::new)
                            .set(button -> button::setText, "Open #1")
                            .set(button -> button::setOnAction, this::open1st)
                            .get(),
                        Pipeliner.of(Button::new)
                            .set(button -> button::setText, "Open #2")
                            .get(),
                        Pipeliner.of(Button::new)
                            .set(button -> button::setText, "Open #3")
                            .get()))
                    .get()))
                .get())
            .accept(stage -> {
                stage.sizeToScene();
                stage.show();
            });
    }

    void open1st(ActionEvent event) {
        var frame = new StageDialogFrame(Pipeliner.of(Stage::new)
            .set(stage -> stage::initStyle, StageStyle.UTILITY)
            .set(stage -> stage::initOwner, primaryStage)
            .set(stage -> stage::initModality, Modality.WINDOW_MODAL)
            .set(stage -> stage::setResizable, false)
            .get());
        System.out.println(
            OptionDialog.showOptionDialog("Message", Type.CONFIRM, asList(
                Option.YES, Option.NO, Option.CANCEL), frame));
    }

}
