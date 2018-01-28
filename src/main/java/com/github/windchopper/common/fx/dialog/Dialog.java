package com.github.windchopper.common.fx.dialog;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

public class Dialog<F extends DialogFrame, M> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.fx.i18n.messages");

    private static final String BUNDLE_KEY__NULL_PARAMETER = "com.github.windchopper.common.fx.nullParameter";

    private final List<DialogAction> actions = new ArrayList<>();

    /*
     *
     */

    private DialogSkeleton skeleton;
    private F frame;
    private M model;

    /*
     *
     */

    public void installSkeleton(DialogSkeleton skeleton) {
        this.skeleton = requireNonNull(skeleton, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "skeleton"));
    }

    public void installFrame(F frame) {
        this.frame = requireNonNull(frame, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "frame"));

    }

    public void installModel(M model) {
        this.model = requireNonNull(model, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "model"));
    }

    public void add(DialogAction action) {
        actions.add(action);
    }

    /*
     *
     */

    protected Pane prepareRootPane() {
        return skeleton.createRootPane(frame, actions);
    }

    /*
     *
     */

    public void show() {
        frame.installRootPane(prepareRootPane());
        frame.showAndWait();
    }

    public void hide() {
        frame.hide();
    }

}
