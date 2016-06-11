package name.wind.common.fx;

import javafx.event.ActionEvent;

import java.util.concurrent.Executor;

public abstract class BlockingAsyncAction extends AsyncAction {

    public BlockingAsyncAction(Executor executor) {
        super(executor);
    }

    @Override public final void asyncHandle(ActionEvent event) {
        disableProperty().set(true);

        try {
            blockingAsyncHandle(event);
        } finally {
            disableProperty().set(false);
        }
    }

    public abstract void blockingAsyncHandle(
        ActionEvent event
    );

}
