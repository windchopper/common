package com.github.windchopper.common.preferences;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class CompositePreferencesStorage<K> extends AbstractPreferencesStorage {

    public class Configurer {

        public CompositePreferencesStorage<K> enough() {
            return CompositePreferencesStorage.this;
        }

    }

    public class LoadConfigurer extends Configurer {

        public LoadConfigurer tryStorage(K stogageKey) {
            loadOrder.add(stogageKey);
            return this;
        }

        public LoadConfigurer loadNewer(boolean loadNewer) {
            CompositePreferencesStorage.this.loadNewer = loadNewer;
            return this;
        }

        public LoadConfigurer propagateOlder(boolean propagateOlder) {
            CompositePreferencesStorage.this.propagateOlder = propagateOlder;
            return this;
        }

        public LoadConfigurer propagateToStorage(K storageKey) {
            propagationTargets.add(storageKey);
            return this;
        }

    }

    public class SaveConfigurer extends Configurer {

        public SaveConfigurer saveToStorage(K storageKey) {
            saveOrder.add(storageKey);
            return this;
        }

    }

    private final Map<K, PreferencesStorage> storages;
    private final Map<String, PreferencesStorage> bufferedChilds = new HashMap<>();

    private LoadConfigurer loadConfigurer;
    private SaveConfigurer saveConfigurer;

    private final List<K> loadOrder;
    private final List<K> propagationTargets;
    private final List<K> saveOrder;

    private boolean loadNewer;
    private boolean propagateOlder;

    public CompositePreferencesStorage(Map<K, PreferencesStorage> storages) {
        this(
            storages,
            new LinkedList<>(),
            new LinkedList<>(),
            new LinkedList<>(),
            false,
            false);

        loadConfigurer = new LoadConfigurer();
        saveConfigurer = new SaveConfigurer();
    }

    CompositePreferencesStorage(
        Map<K, PreferencesStorage> storages,
        List<K> loadOrder,
        List<K> propagationTargets,
        List<K> saveOrder,
        boolean loadNewer,
        boolean propagateOlder) {

        this.storages = storages;
        this.loadOrder = loadOrder;
        this.propagationTargets = propagationTargets;
        this.saveOrder = saveOrder;
        this.loadNewer = loadNewer;
        this.propagateOlder = propagateOlder;
    }

    IllegalStateException alreadyConfigured() {
        return new IllegalStateException("Already configured");
    }

    public LoadConfigurer onLoad() {
        return Optional.ofNullable(loadConfigurer)
            .orElseThrow(this::alreadyConfigured);
    }

    public SaveConfigurer onSave() {
        return Optional.ofNullable(saveConfigurer)
            .orElseThrow(this::alreadyConfigured);
    }

    @Override public Optional<PreferencesEntryText> valueImpl(String name) {
        var presentValues = loadOrder.stream()
            .map(storages::get)
            .map(storage -> storage.value(name));

        Optional<PreferencesEntryText> selectedValue;

        if (loadNewer) {
            selectedValue = presentValues
                .filter(Optional::isPresent)
                .map(Optional::get)
                .max(comparing(PreferencesEntryText::modificationTime));
        } else {
            selectedValue = presentValues
                .filter(Optional::isPresent)
                .findFirst()
                .flatMap(identity());
        }

        selectedValue.ifPresent(presentValue -> propagationTargets.stream()
            .map(storages::get)
            .forEach(storage -> {
                var existingValue = storage.value(name);

                if (existingValue.isEmpty() || propagateOlder && existingValue.get().modificationTime().isBefore(presentValue.modificationTime())) {
                    storage.saveValue(name, presentValue.text());
                }
            }));

        return selectedValue;
    }

    @Override public void saveValueImpl(String name, String text) {
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.saveValue(name, text));
    }

    @Override public void removeValueImpl(String name) {
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.removeValue(name));
    }

    @Override public PreferencesStorage childImpl(String name) {
        return bufferedChilds.computeIfAbsent(name, missingName -> new CompositePreferencesStorage<>(
            loadOrder.stream()
                .map(storageKey -> new SimpleEntry<>(storageKey, storages.get(storageKey).child(name)))
                .collect(toMap(Entry::getKey, Entry::getValue)),
            loadOrder,
            propagationTargets,
            saveOrder,
            loadNewer,
            propagateOlder));
    }

    @Override public void removeChildImpl(String name) {
        bufferedChilds.remove(name);
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.removeChild(name));
    }

    @Override public Set<String> valueNamesImpl() {
        return loadOrder.stream()
            .map(storages::get)
            .flatMap(storage -> storage.valueNames().stream())
            .collect(toSet());
    }

    @Override public Set<String> childNamesImpl() {
        return loadOrder.stream()
            .map(storages::get)
            .flatMap(storage -> storage.childNames().stream())
            .collect(toSet());
    }

}
