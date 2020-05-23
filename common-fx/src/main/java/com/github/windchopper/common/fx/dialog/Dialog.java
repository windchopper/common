package com.github.windchopper.common.fx.dialog;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Dialog<S extends DialogSkeleton, F extends DialogFrame, M> {

    protected final S skeleton;
    protected final F frame;
    protected final M model;

    protected final List<DialogAction> actions = new ArrayList<>();

    public Dialog(S skeleton, F frame, M model) {
        this.skeleton = requireNonNull(skeleton, "skeleton");
        this.frame = requireNonNull(frame, "frame");
        this.model = requireNonNull(model, "model");
    }

    public Dialog<S, F, M> addAction(DialogAction action) {
        actions.add(action);
        return this;
    }

    protected Pane buildRootPane() {
        return skeleton.buildRootPane(actions);
    }

    public void showAndWait() {
        frame.installRootPane(buildRootPane());
        frame.showAndWait();
    }

    public void show() {
        frame.installRootPane(buildRootPane());
        frame.show();
    }

    public void hide() {
        frame.hide();
    }

}
