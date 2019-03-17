package com.github.windchopper.common.util.stream;

@FunctionalInterface public interface FailableRunnable<E extends Throwable> {

    void run() throws E;

    static <E extends Throwable> FailableRunnableResult failsafeRun(FailableRunnable<E> failableRunnable) {
        try {
            failableRunnable.run();
            return new FailableRunnableResult(null);
        } catch (Throwable thrown) {
            return new FailableRunnableResult(thrown);
        }
    }

}
