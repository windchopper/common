package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FailableRunnable;
import com.github.windchopper.common.util.stream.FailableSupplier;

import java.util.Optional;
import java.util.Set;

public abstract class AbstractPreferencesStorage implements PreferencesStorage {

    public abstract Optional<PreferencesEntryText> valueImpl(String name) throws Exception;
    public abstract void saveValueImpl(String name, String value) throws Exception;
    public abstract void removeValueImpl(String name) throws Exception;
    public abstract PreferencesStorage childImpl(String name) throws Exception;
    public abstract void removeChildImpl(String name) throws Exception;
    public abstract Set<String> valueNamesImpl() throws Exception;
    public abstract Set<String> childNamesImpl() throws Exception;

    private <T> T wrap(FailableSupplier<T, Exception> supplier) throws PreferencesException {
        try {
            return supplier.get();
        } catch (Exception thrown) {
            throw new PreferencesException(thrown.getMessage(), thrown);
        }
    }

    private void wrap(FailableRunnable<Exception> runnable) throws PreferencesException {
        try {
            runnable.run();
        } catch (Exception thrown) {
            throw new PreferencesException(thrown.getMessage(), thrown);
        }
    }

    @Override public Optional<PreferencesEntryText> value(String name) throws PreferencesException {
        return wrap(() -> valueImpl(name));
    }

    @Override public void saveValue(String name, String text) throws PreferencesException {
        wrap(() -> saveValueImpl(name, text));
    }

    @Override public void removeValue(String name) throws PreferencesException {
        wrap(() -> removeValueImpl(name));
    }

    @Override public PreferencesStorage child(String name) throws PreferencesException {
        return wrap(() -> childImpl(name));
    }

    @Override public void removeChild(String name) throws PreferencesException {
        wrap(() -> removeChildImpl(name));
    }

    @Override public Set<String> valueNames() throws PreferencesException {
        return wrap(this::valueNamesImpl);
    }

    @Override public Set<String> childNames() throws PreferencesException {
        return wrap(this::childNamesImpl);
    }

}
