package com.github.windchopper.common.fx.dialog;

import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.util.ResourceBundle;

public class ExceptionDialog<F extends DialogFrame> extends Dialog<ExceptionDialogSkeleton, F, ExceptionDialogModel> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private static final String BUNDLE_KEY__CLOSE = "com.github.windchopper.common.fx.dialog.ExceptionDialog.close";

    public ExceptionDialog(F frame, ExceptionDialogModel model) {
        this(new ExceptionDialogSkeleton(), frame, model);
    }

    public ExceptionDialog(ExceptionDialogSkeleton skeleton, F frame, ExceptionDialogModel model) {
        super(skeleton, frame, model);
    }

    @Override protected Pane buildRootPane() {
        String message = model.getException().getMessage();

        if (message == null || message.trim().isEmpty()) {
            message = model.getException().getClass().getCanonicalName();

            if (message.length() > 40) {
                message = "..." + message.substring(message.length() - 37);
            }
        }

        if (message.length() > 40) {
            message = message.substring(0, 37) + "...";
        }

        skeleton.textProperty().set(message);
        skeleton.imageProperty().set(OptionDialog.Type.ERROR.image());
        skeleton.exceptionProperty().set(model.getException());

        DialogAction closeAction = new DialogAction(this, DialogAction.ThreatThreshold.REJECT.min());
        closeAction.textProperty().set(bundle.getString(BUNDLE_KEY__CLOSE));
        closeAction.addHandler((actionEvent, action) -> frame.hide());
        addAction(closeAction);

        var rootPane = skeleton.buildRootPane(actions);
        rootPane.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);

        return rootPane;
    }

}
