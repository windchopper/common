package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.*;

import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toMap;

public class CompositeEntry<K, T> implements PreferencesEntry<T> {

    public static class StorageComposition<K> {

        private final Map<K, PreferencesStorage> storages;

        private final List<K> loadOrder = new LinkedList<>();
        private final List<K> propagationTargets = new LinkedList<>();
        private final List<K> saveOrder = new LinkedList<>();

        private boolean loadNewer;
        private boolean propagateOlder;

        public StorageComposition(Map<K, PreferencesStorage> storages) {
            this.storages = storages;
        }

        public StorageComposition<K> loadFrom(K entryKey) {
            loadOrder.add(entryKey);
            return this;
        }

        public StorageComposition<K> loadNewer() {
            loadNewer = true;
            return this;
        }

        public StorageComposition<K> propagateOlder() {
            propagateOlder = true;
            return this;
        }

        public StorageComposition<K> propagateTo(K entryKey) {
            propagationTargets.add(entryKey);
            return this;
        }

        public StorageComposition<K> saveTo(K entryKey) {
            saveOrder.add(entryKey);
            return this;
        }

    }

    private final StorageComposition<K> storageComposition;
    private final Map<K, PreferencesEntry<T>> entries;

    public CompositeEntry(StorageComposition<K> storageComposition, String name, PreferencesEntryType<T, ?> type) {
        entries = (this.storageComposition = storageComposition).storages.entrySet().stream()
            .collect(toMap(
                Map.Entry::getKey,
                entry -> new StandardEntry<>(entry.getValue(), name, type)));
    }

    @Override public PreferencesEntryValueHolder<T> load() throws PreferencesException {
        var nonEmptyHolders = new ArrayList<PreferencesEntryValueHolder<T>>();

        for (K entryKey : storageComposition.loadOrder) {
            var holder = entries.get(entryKey).load();

            if (holder.getValue() != null) {
                nonEmptyHolders.add(holder);
            }
        }

        Optional<PreferencesEntryValueHolder<T>> selectedHolder;

        Comparator<PreferencesEntryValueHolder<?>> holderComparator = comparing(PreferencesEntryValueHolder::getTimestamp,
            nullsFirst(naturalOrder()));

        if (storageComposition.loadNewer) {
            selectedHolder = nonEmptyHolders.stream()
                .max(holderComparator);
        } else {
            selectedHolder = nonEmptyHolders.stream()
                .findFirst();
        }

        selectedHolder.ifPresent(holder -> storageComposition.propagationTargets.stream()
            .map(entries::get).forEach(entry -> {
                var existingHolder = entry.load();

                if (existingHolder.isEmpty() || storageComposition.propagateOlder && holderComparator.compare(holder, existingHolder) >= 0) {
                    entry.save(holder.getValue());
                }
            }));

        return selectedHolder
            .orElseGet(PreferencesEntryValueHolder::new);
    }

    @Override public void save(T value) throws PreferencesException {
        for (K entryKey : storageComposition.saveOrder) {
            entries.get(entryKey).save(value);
        }
    }

}
