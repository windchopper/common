package com.github.windchopper.common.monitoring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsCollector implements StatisticsCollectorMXBean {

    public interface Holder {
        StatisticsCollector instance = new StatisticsCollector();
    }

    private boolean enabled;

    private final Map<String, Measurements> namedMeasurements = new ConcurrentHashMap<>();

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void registerStart(String name) {
        if (!enabled) return;
        namedMeasurements.computeIfAbsent(name, missingName -> new Measurements())
            .registerStart();
    }

    public void registerSuccess(String name, long executionTimeMilliseconds) {
        if (!enabled) return;
        namedMeasurements.get(name)
            .registerSuccess(executionTimeMilliseconds);
    }

    public void registerFail(String name, long executionTimeMilliseconds) {
        if (!enabled) return;
        namedMeasurements.get(name)
            .registerFail(executionTimeMilliseconds);
    }

    @Override
    public Statistics[] gatherStatistics(long startTimeSeconds, long endTimeSeconds) {
        return namedMeasurements.entrySet().stream()
            .map(entry -> entry.getValue().gatherStatistics(entry.getKey(), startTimeSeconds, endTimeSeconds))
            .toArray(Statistics[]::new);
    }

}
