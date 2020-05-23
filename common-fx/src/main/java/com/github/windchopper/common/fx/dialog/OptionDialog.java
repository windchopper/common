package com.github.windchopper.common.fx.dialog;

import com.github.windchopper.common.util.Pipeliner;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OptionDialog<F extends DialogFrame> extends Dialog<CaptionedDialogSkeleton, F, OptionDialogModel> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private static final String BUNDLE_KEY__OK = "com.github.windchopper.common.fx.dialog.OptionDialog.ok";
    private static final String BUNDLE_KEY__CANCEL = "com.github.windchopper.common.fx.dialog.OptionDialog.cancel";
    private static final String BUNDLE_KEY__YES = "com.github.windchopper.common.fx.dialog.OptionDialog.yes";
    private static final String BUNDLE_KEY__NO = "com.github.windchopper.common.fx.dialog.OptionDialog.no";

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
            try {
                try (InputStream inputStream = getClass().getResourceAsStream("/com/github/windchopper/common/fx/dialog/option/" + imageFile)) {
                    return new Image(inputStream);
                }
            } catch (IOException | NullPointerException thrown) {
                Logger logger = Logger.getLogger(getDeclaringClass().getName());

                if (logger.isLoggable(Level.SEVERE)) {
                    logger.log(Level.SEVERE, thrown.getMessage(), thrown);
                }
            }

            return null;
        }

    }

    public enum Option {

        OK(bundle.getString(BUNDLE_KEY__OK), DialogAction.ThreatThreshold.ACCEPT.min() + 1),
        CANCEL(bundle.getString(BUNDLE_KEY__CANCEL), DialogAction.ThreatThreshold.REJECT.max() - 1),
        YES(bundle.getString(BUNDLE_KEY__YES), DialogAction.ThreatThreshold.ACCEPT.min() + 2),
        NO(bundle.getString(BUNDLE_KEY__NO), DialogAction.ThreatThreshold.REJECT.max() - 2);

        private final String label;
        private final int threat;

        Option(String label, int threat) {
            this.label = label;
            this.threat = threat;
        }

        DialogAction newAction(Dialog<?, ?, ?> dialog) {
            return Pipeliner.of(() -> new DialogAction(dialog, threat))
                .accept(action -> action.textProperty().set(label))
                .get();
        }

    }

    public OptionDialog(F frame, OptionDialogModel model) {
        this(new CaptionedDialogSkeleton(), frame, model);
    }

    public OptionDialog(CaptionedDialogSkeleton skeleton, F frame, OptionDialogModel model) {
        super(skeleton, frame, model);
    }

    @Override protected Pane buildRootPane() {
        skeleton.textProperty().set(model.getMessage());
        skeleton.imageProperty().set(model.getType().image());

        for (var option : model.getAvailableOptions()) {
            var dialogAction = option.newAction(this);
            dialogAction.addHandler((actionEvent, action) -> model.setSelectedOption(option));
            actions.add(dialogAction);
        }

        var rootPane = skeleton.buildRootPane(actions);
        rootPane.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);

        return rootPane;
    }

}
