package com.github.windchopper.common.fx.dialog;

import javafx.scene.layout.Pane;

public interface DialogFrame {

    void installRootPane(Pane rootPane);
    void show();
    void showAndWait();
    void hide();

}
