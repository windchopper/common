package com.github.windchopper.common.fx.behavior;

import javafx.scene.control.Spinner;

public class SpinnerCommitOnBlurBehavior<T> implements Behavior<Spinner<T>> {

    @Override public void apply(Spinner<T> target) {
        target.getEditor().focusedProperty().addListener(
            (observable, oldFocused, newFocused) -> {
                if (oldFocused) {
                    target.getValueFactory().setValue(
                        target.getValueFactory().getConverter().fromString(
                            target.getEditor().getText()
                        )
                    );
                }
            }
        );
    }

}
