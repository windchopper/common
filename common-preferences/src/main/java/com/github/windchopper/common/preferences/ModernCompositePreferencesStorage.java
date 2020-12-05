package com.github.windchopper.common.preferences;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ModernCompositePreferencesStorage<K> extends AbstractPreferencesStorage {

    public class Configurer {

        public ModernCompositePreferencesStorage<K> enough() {
            return ModernCompositePreferencesStorage.this;
        }

    }

    public class LoadConfigurer extends Configurer {

        public LoadConfigurer tryStorage(K stogageKey) {
            loadOrder.add(stogageKey);
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

    public ModernCompositePreferencesStorage(Map<K, PreferencesStorage> storages) {
        this(
            storages,
            new LinkedList<>(),
            new LinkedList<>(),
            new LinkedList<>());

        loadConfigurer = new LoadConfigurer();
        saveConfigurer = new SaveConfigurer();
    }

    ModernCompositePreferencesStorage(
        Map<K, PreferencesStorage> storages,
        List<K> loadOrder,
        List<K> propagationTargets,
        List<K> saveOrder) {

        this.storages = storages;
        this.loadOrder = loadOrder;
        this.propagationTargets = propagationTargets;
        this.saveOrder = saveOrder;
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

    @Override public String value(String name, String defaultValue) {
        Optional<String> value = loadOrder.stream()
            .map(storages::get)
            .map(storage -> storage.value(name, null))
            .filter(Objects::nonNull)
            .findFirst();

        value.ifPresent(presentValue -> propagationTargets.stream()
            .map(storages::get)
            .forEach(storage -> storage.putValue(name, presentValue)));

        return value.orElse(defaultValue);
    }

    @Override public void putValue(String name, String value) {
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.putValue(name, value));
    }

    @Override public void removeValue(String name) {
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.removeValue(name));
    }

    @Override public Set<String> valueNames() {
        return loadOrder.stream()
            .map(storages::get)
            .flatMap(storage -> storage.valueNames().stream())
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return loadOrder.stream()
            .map(storages::get)
            .flatMap(storage -> storage.childNames().stream())
            .collect(toSet());
    }

    @Override public PreferencesStorage child(String name) {
        return bufferedChilds.computeIfAbsent(name, missingName -> new ModernCompositePreferencesStorage<>(
            loadOrder.stream()
                .map(storageKey -> new SimpleEntry<>(storageKey, storages.get(storageKey).child(name)))
                .collect(toMap(Entry::getKey, Entry::getValue)),
            loadOrder,
            propagationTargets,
            saveOrder));
    }

    @Override public void removeChild(String name) {
        bufferedChilds.remove(name);
        saveOrder.stream()
            .map(storages::get)
            .forEach(storage -> storage.removeChild(name));
    }

}
