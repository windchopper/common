package name.wind.common.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FailableFunctionResult<I, O> {

    private final I value;
    private final O outcome;
    private final Throwable exception;

    public FailableFunctionResult(I value, O outcome, Throwable exception) {
        this.value = value;
        this.outcome = outcome;
        this.exception = exception;
    }

    public boolean succeeded() {
        return exception == null;
    }

    public boolean failed() {
        return exception != null;
    }

    public void onSuccess(BiConsumer<I, O> handler) {
        if (exception == null) {
            handler.accept(value, outcome);
        }
    }

    public void onFailure(BiConsumer<I, Throwable> handler) {
        if (exception != null) {
            handler.accept(value, exception);
        }
    }

    public O revover(BiFunction<I, Throwable, O> recoverer) {
        return exception != null ? recoverer.apply(value, exception) : outcome;
    }

}
