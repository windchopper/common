package com.github.windchopper.common.fx.dialog;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

public class StageDialogFrame implements DialogFrame {

    private static final Collection<StageStyle> unborderedStageStyles = List.of(
        StageStyle.TRANSPARENT, StageStyle.UNDECORATED);

    private final Stage stage;

    public StageDialogFrame(Stage stage) {
        this.stage = requireNonNull(stage, "stage");
    }

    @Override public void installRootPane(Pane rootPane) {
        stage.setScene(new Scene(requireNonNull(rootPane, "contentPane")));

        if (unborderedStageStyles.contains(stage.getStyle())) {
            rootPane.setEffect(
                new DropShadow());
            rootPane.setBorder(
                new Border(new BorderStroke(
                    Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
        }
    }

    @Override public void show() {
        stage.show();
    }

    @Override public void showAndWait() {
        stage.showAndWait();
    }

    @Override public void hide() {
        stage.hide();
    }

}
