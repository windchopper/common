package com.github.windchopper.common.preferences;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPreferencesStorage implements PreferencesStorage {

    protected static final Logger logger = Logger.getLogger("com.github.windchopper.common.preferences");

    protected void logError(Exception exception) {
        logger.log(Level.SEVERE, "Unexpected error", exception);
    }

    @FunctionalInterface protected interface FaultyAction<R> {
        R invoke() throws Exception;
    }

    protected <R> R invoke(FaultyAction<R> faultyAction, Consumer<Exception> faultConsumer, Supplier<R> faultResultSupplier) {
        try {
            return faultyAction.invoke();
        } catch (Exception thrown) {
            faultConsumer.accept(thrown);
        }

        return faultResultSupplier.get();
    }

}
