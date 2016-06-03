package ru.wind.common.fx;

import javafx.event.ActionEvent;

import java.util.concurrent.Executor;

public abstract class AsyncAction extends Action {

    private final Executor executor;

    public AsyncAction(Executor executor) {
        this.executor = executor;
    }

    @Override public final void handle(ActionEvent event) {
        executor.execute(
            () -> asyncHandle(event)
        );
    }

    public abstract void asyncHandle(
        ActionEvent event
    );

}
