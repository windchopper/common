package com.github.windchopper.common.preferences;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class CompositePreferencesStorage implements PreferencesStorage {

    public static class Mediator extends PreferencesStorageDecorator {

        final int loadOrder;
        final int saveOrder;
        final boolean propagateTarget;

        public Mediator(PreferencesStorage storage, int loadOrder, int saveOrder, boolean propagateTarget) {
            super(storage);
            this.loadOrder = loadOrder;
            this.saveOrder = saveOrder;
            this.propagateTarget = propagateTarget;
        }

    }

    static class Pair {

        final Mediator mediator;
        final String value;

        Pair(Mediator mediator, String value) {
            this.mediator = mediator;
            this.value = value;
        }

    }

    private final static Predicate<Mediator> loadSelector = mediator -> mediator.loadOrder > 0;
    private final static Comparator<Mediator> loadComparator = Comparator.comparingInt(mediator -> mediator.loadOrder);
    private final static Predicate<Mediator> saveSelector = mediator -> mediator.saveOrder > 0;
    private final static Comparator<Mediator> saveComparator = Comparator.comparingInt(mediator -> mediator.saveOrder);

    private final Set<Mediator> mediators;
    private final Map<String, PreferencesStorage> bufferedChilds = new HashMap<>();

    public CompositePreferencesStorage(Collection<Mediator> mediators) {
        this.mediators = new HashSet<>(mediators);
    }

    @Override public String value(String name, String defaultValue) {
        var mediatorWithValue = mediators.stream()
            .filter(loadSelector)
            .sorted(loadComparator)
            .map(mediator -> new Pair(mediator, mediator.value(name, null)))
            .filter(pair -> pair.value != null)
            .findFirst();

        mediatorWithValue.ifPresent(pair -> mediators.stream()
            .filter(saveSelector)
            .filter(mediator -> mediator.propagateTarget)
            .sorted(saveComparator)
            .filter(mediator -> mediator != pair.mediator)
            .forEach(mediator -> mediator.putValue(name, pair.value)));

        return mediatorWithValue.map(pair -> pair.value).orElse(defaultValue);
    }

    @Override public void putValue(String name, String value) {
        mediators.stream()
            .filter(saveSelector)
            .sorted(saveComparator)
            .forEach(mediator -> mediator.putValue(name, value));
    }

    @Override public void removeValue(String name) {
        mediators.stream()
            .filter(saveSelector)
            .sorted(saveComparator)
            .forEach(mediator -> mediator.removeValue(name));
    }

    @Override public Set<String> valueNames() {
        return mediators.stream()
            .filter(loadSelector)
            .sorted(loadComparator)
            .flatMap(mediator -> mediator.valueNames().stream())
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return mediators.stream()
            .filter(loadSelector)
            .sorted(loadComparator)
            .flatMap(mediator -> mediator.childNames().stream())
            .collect(toSet());
    }

    @Override public PreferencesStorage child(String name) {
        return bufferedChilds.computeIfAbsent(name, key -> new CompositePreferencesStorage(
            mediators.stream()
                .map(mediator -> new Mediator(
                    mediator.storage.child(name),
                    mediator.loadOrder,
                    mediator.saveOrder,
                    mediator.propagateTarget))
                .collect(toSet())));
    }

    @Override public void removeChild(String name) {
        bufferedChilds.remove(name);
        mediators.stream()
            .filter(saveSelector)
            .sorted(saveComparator)
            .forEach(mediator -> mediator.removeChild(name));
    }

}
