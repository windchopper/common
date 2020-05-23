package com.github.windchopper.common.fx.dialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class TestApp extends Application {

    Stage primaryStage;

    @Override public void start(Stage primaryStage) throws Exception {
        Button showOkCancelDialogButton = new Button();
        showOkCancelDialogButton.setText("Ok/Cancel dialog");
        showOkCancelDialogButton.setOnAction(this::showOkCancelDialog);

        Button showExceptionDialogButton = new Button();
        showExceptionDialogButton.setText("Exception dialog");
        showExceptionDialogButton.setOnAction(this::showExceptionDialog);

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(showOkCancelDialogButton);
        toolBar.getItems().add(showExceptionDialogButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolBar);

        this.primaryStage = primaryStage;

        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.setTitle("TestApp");
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    Stage prepareDialogStage() {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);

        return stage;
    }

    void showOkCancelDialog(ActionEvent actionEvent) {
        OptionDialogModel model = new OptionDialogModel();
        model.setType(OptionDialog.Type.INFORMATION);
        model.setMessage("Dialog message");
        model.setAvailableOptions(List.of(OptionDialog.Option.OK, OptionDialog.Option.CANCEL));

        new OptionDialog<>(new StageDialogFrame(prepareDialogStage()), model)
            .showAndWait();
    }

    void showExceptionDialog(ActionEvent actionEvent) {
        ExceptionDialogModel model = new ExceptionDialogModel();
        model.setException(new RuntimeException());

        new ExceptionDialog<>(new StageDialogFrame(prepareDialogStage()), model)
            .showAndWait();
    }

}
