package com.github.windchopper.common.fx.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DialogActionHandler<F extends DialogFrame> implements EventHandler<ActionEvent> {

    private final EventHandler<ActionEvent> wrappedHandler;
    private final F frame;

    public DialogActionHandler(EventHandler<ActionEvent> wrappedHandler, F frame) {
        this.wrappedHandler = wrappedHandler;
        this.frame = frame;
    }

    @Override public void handle(ActionEvent event) {
        wrappedHandler.handle(event);
        frame.hide();
    }

}
