package com.github.windchopper.common.util.stream;

import java.util.function.Supplier;

@FunctionalInterface public interface FailableRunnable<E extends Throwable> {

    void run() throws E;

    static <E extends Throwable> Supplier<FailableRunnableResult> wrap(FailableRunnable<E> failableRunnable) {
        return () -> failsafeRun(failableRunnable);
    }

    static <E extends Throwable> FailableRunnableResult failsafeRun(FailableRunnable<E> failableRunnable) {
        try {
            failableRunnable.run();
            return new FailableRunnableResult(null);
        } catch (Throwable thrown) {
            return new FailableRunnableResult(thrown);
        }
    }

}
